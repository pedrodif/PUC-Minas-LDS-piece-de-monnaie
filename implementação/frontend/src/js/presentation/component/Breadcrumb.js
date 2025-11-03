export class Breadcrumb {
    static INSTANCE = null

    constructor() {        
        if (Breadcrumb.INSTANCE) {
            throw new Error('Use Breadcrumb.getBreadcrumb() para acessar a instância.')
        }

        this.trail = []
        this.list = this.mountList()
    }

    mountList() {
        const ul = document.createElement('ul')
        ul.className = 'breadcrumb-lista'

        const container = document.createElement('nav')
        container.className = 'breadcrumb-container'
        container.appendChild(ul)

        const main = document.querySelector('main')
        main.prepend(container)

        return ul
    }

    mountSeparator() {
        const separator = document.createElement('span')
        separator.textContent = ' / '
        separator.classList.add('breadcrumb-separador')
        return separator
    }

    mountHomeAnchor(item) {
        const icon = document.createElement('i')
        icon.classList.add('fa-solid', 'fa-house')

        const anchor = document.createElement('a')
        anchor.href = item.link
        anchor.dataset.toggle = 'tooltip'
        anchor.title = 'Home'
        anchor.classList.add('breadcrumb-link')
        anchor.appendChild(icon)

        return anchor
    }

    mountDefaultAnchor(item) {
        const anchor = document.createElement('a')
        anchor.href = item.link
        anchor.classList.add('breadcrumb-link')
        anchor.innerHTML = item.label
        return anchor
    }

    mountBreadcrumbItem(item) {
        const breadcrumbItem = document.createElement('li')
        breadcrumbItem.classList.add('breadcrumb-item')
        breadcrumbItem.appendChild(item)

        this.list.appendChild(breadcrumbItem)
    }

    render() {
        this.list.innerHTML = ''

        this.trail.forEach((item, index) => {
            const anchor = item.label === 'home' 
                ? this.mountHomeAnchor(item) 
                : this.mountDefaultAnchor(item)

            this.mountBreadcrumbItem(anchor)

            if (index < this.trail.length - 1) {
                const separator = this.mountSeparator()
                this.mountBreadcrumbItem(separator)
            }
        })
    }

    add(items) {
        this.trail = items
        this.render()
    }

    static getBreadcrumb() {
        if (!Breadcrumb.INSTANCE) {
            Breadcrumb.INSTANCE = new Breadcrumb()
        }
        return Breadcrumb.INSTANCE
    }
}
