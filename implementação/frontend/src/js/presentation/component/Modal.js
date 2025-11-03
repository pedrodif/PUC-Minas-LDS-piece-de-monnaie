export class Modal {
    constructor(modalElement) {
        this.modalElement = modalElement

        this.openingObservers = []
        this.closingObservers = []
        this.closeButton = this.modalElement?.querySelector('.fechar')

        this.closeButton?.addEventListener('click', this.close)
        document.addEventListener('keydown', this.handleKeyDown)
    }

    addOpeningObserver(callback) {
        this.openingObservers.push(callback)
    }

    addClosingObserver(callback) {
        this.closingObservers.push(callback)
    }

    open = (conditional) => {
        if (conditional) this.openingObservers.forEach(callback => callback())
        this.modalElement.style.display = 'flex'
    }

    close = () => {
        if (this.isOpen()) {
            this.closingObservers.forEach(callback => callback())
            this.modalElement.style.display = 'none'
        }
    }

    isOpen = () => {
        return this.modalElement.style.display === 'flex'
    }

    handleKeyDown = (event) => {
        if (event.key === 'Escape' && this.isOpen()) {
            this.close()
        }
    }
}