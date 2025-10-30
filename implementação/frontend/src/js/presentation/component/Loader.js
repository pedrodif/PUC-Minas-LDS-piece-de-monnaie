export class Loader {
    static INSTANCE = null

    constructor() {
        if (Loader.INSTANCE) {
            throw new Error("Use Loader.getLoader() para acessar a instância.")
        }

        this.loader = this.mountLoader()
    }

    mountLoader() {
        const loader = document.createElement('div')
        loader.className = 'loader'
        document.body.appendChild(loader)
        return loader
    }

    mountSpinner() {
        const spinner = document.createElement('div')
        spinner.className = 'spinner'
        return spinner
    }

    show() {
        this.loader.style.display = 'flex'

        if (!this.loader.querySelector('.spinner')) {
            this.loader.appendChild(this.mountSpinner())
        }
    }

    hide() {
        this.loader.firstChild?.remove()
        this.loader.style.display = 'none'
    }

    static getLoader() {
        if (!Loader.INSTANCE) {
            Loader.INSTANCE = new Loader()
        }
        return Loader.INSTANCE
    }
}
