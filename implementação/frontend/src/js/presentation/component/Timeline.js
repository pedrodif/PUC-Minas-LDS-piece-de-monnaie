import { Empty } from './Empty.js'

export class Timeline {
    constructor(container) {
        this.container = container
        this.container.classList.add('timeline')
        this.items = []

        this.empty = new Empty({
            figcaption: 'Nenhuma movimentação por aqui...Todas as suas transações futuras serão exibidas nesta linha do tempo.',
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

    }

    render(items) {
        this.items = items
        this.container.innerHTML = ''
        // this.container.classList.remove('timeline')

        if (this.items.length === 0) {
            this.mountEmptyState()
            return
        }

        const fragment = document.createDocumentFragment()
        this.items.forEach((item, index) => {
            fragment.appendChild(this.mountItem(item, index))
        })

        this.container.appendChild(fragment)
    }
}