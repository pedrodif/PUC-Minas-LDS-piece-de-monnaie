import { Session } from '../../middleware/Session.js'
import { Utilities } from '../../helper/Utilities.js'
import { Empty } from './Empty.js'

export class Timeline {
    constructor(container) {
        this.container = container
        this.user = Session.userProvider()
        this.items = []

        this.empty = new Empty({
            figcaption: 'Nenhuma movimentação por aqui...Todas as suas transações futuras serão exibidas em uma linha do tempo.',
            displayTitle: false,
            margin: '0 0',
            padding: '86px',
        })
    }

    mountEmptyState() {
        if (this.empty) {
            this.container.appendChild(this.empty.render())
        }
    }

    mountItem(item, index) {
        const icone = document.createElement('i')

        icone.classList.add('fa-solid',
            item.tipo === 'TROCA'
                ? 'fa-hand-holding-heart'
                : this.user.id !== item.emissor.id
                    ? 'fa-trophy'
                    : 'fa-circle-check'
        )

        const milestone = document.createElement('div')
        milestone.classList.add('timeline-marco')
        milestone.appendChild(icone)

        const summary = document.createElement('summary')
        summary.textContent = item.tipo === 'ENVIO'
            ? item.mensagem
            : `Vantagem Escolhida: ${item.vantagem.descricao}`

        const time = document.createElement('time')
        time.textContent = `Data da movimentação: ${Utilities.formatDateTime(item.feitaEm)}`

        const transactionPartner = document.createElement('p')
        const amount = document.createElement('p')

        if (this.user.id === item.emissor.id) {
            transactionPartner.textContent = item.tipo === 'ENVIO'
                ? `Enviado para: ${item.receptor.nome}`
                : `Oferecido por: ${item.receptor.nome}`

            amount.textContent = item.tipo === 'ENVIO'
                ? `${item.montante} moedas enviadas`
                : `${item.montante} moedas utilizadas`
        } else {
            transactionPartner.textContent = item.tipo === 'ENVIO'
                ? `Enviado por: ${item.emissor.nome}`
                : `Troca realizada por: ${item.emissor.nome}`
            amount.textContent = item.tipo === 'ENVIO' 
                ? `${item.montante} moedas creditadas`
                : `${item.montante} moedas trocadas`
        }

        const details = document.createElement('details')
        details.append(summary, time, transactionPartner, amount)

        const info = document.createElement('div')
        info.classList.add('timeline-info',
            item.tipo === 'TROCA'
                ? 'troca'
                : this.user.id !== item.emissor.id
                    ? 'recebido'
                    : 'envio')

        info.appendChild(details)

        const delay = index * 0.1
        const timeLineItem = document.createElement('div')
        timeLineItem.classList.add('timeline-item')
        timeLineItem.style.animationDelay = `${delay}s`
        timeLineItem.style.animationDuration = `0.5s`
        timeLineItem.append(milestone, info)

        return timeLineItem
    }

    render(items) {
        this.items = items
        this.container.innerHTML = ''
        this.container.classList.remove('timeline')

        if (this.items.length === 0) {
            this.container.style.width = '100%'
            this.mountEmptyState()
            return
        }

        const fragment = document.createDocumentFragment()
        this.items.forEach((item, index) => {
            fragment.appendChild(this.mountItem(item, index))
        })

        this.container.classList.add('timeline')
        this.container.appendChild(fragment)
    }
}