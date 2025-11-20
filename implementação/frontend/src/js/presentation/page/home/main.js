
import { Url } from '../../../helper/Url.js'
import { Header } from '../../component/Header.js'
import { Notifier } from '../../../helper/Notifier.js'
import { Timeline } from './../../component/Timeline.js'
import { Service } from '../../../application/Service.js'
import { Session } from './../../../middleware/Session.js'

const routes = Url.mountRoutes()
const user = Session.userProvider()
const shouldNotify = Session.shouldNotify()

const p = document.querySelector('p')
const button = document.querySelector('button')
const figcaption = document.querySelector('figcaption')

const header = new Header()
const timeline = new Timeline(document.querySelector('#timeline-container'))

const transactionsService = new Service({
    endpoint: '/api/transacoes',
})

const BUTTON_CONFIGS = {
    ALUNO: {
        text: '<i class="fa-solid fa-hand-holding-heart"></i> Resgatar Vantagens',
        balance: {
            display: 'flex',
            innerHTML: `<i class="fa-solid fa-coins"></i> ${user.quantidadeMoeda} Moedas`
        },
        onClick: () => window.location.href = routes.getReward
    },
    EMPRESA: {
        text: '<i class="fa-solid fa-hands-holding-circle"></i> Gerenciar Vantagens',
        balance: {
            display: 'none',
        },
        onClick: () => window.location.href = routes.manageReward
    },
    PROFESSOR: {
        text: '<i class="fa-solid fa-graduation-cap"></i> Meus Alunos',
        balance: {
            display: 'flex',
            innerHTML: `<i class="fa-solid fa-coins"></i> ${user.quantidadeMoeda} Moedas`
        },
        onClick: () => window.location.href = routes.sendReward
    }
}

const customizeHome = () => {
    button.innerHTML = BUTTON_CONFIGS[user.tipo].text
    button.onclick = BUTTON_CONFIGS[user.tipo].onClick

    p.style.display = BUTTON_CONFIGS[user.tipo].balance.display

    if (BUTTON_CONFIGS[user.tipo].balance.display === 'flex')
        p.innerHTML = BUTTON_CONFIGS[user.tipo].balance.innerHTML

    figcaption.textContent = `Seja bem vindo(a) ao Pièce de Monnaie ${user.nome}`
}

header.render()
customizeHome()

if (shouldNotify) {
    Notifier.info('Login realizado com sucesso!')
}

const data = await transactionsService.getAll()
timeline.render(data)