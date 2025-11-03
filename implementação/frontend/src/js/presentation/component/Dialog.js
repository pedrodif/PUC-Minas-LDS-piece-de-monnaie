export class Dialog {
    constructor(message) {
        this.message = message
    }

    mountDialog() {
        this.container = document.createElement('section')
        this.container.classList.add('dialog-container')
        this.container.setAttribute('role', 'dialog')
        this.container.setAttribute('aria-modal', 'true')
        this.container.setAttribute('aria-labelledby', 'dialog-title')

        this.confirmButton = document.createElement('button')
        this.confirmButton.textContent = 'Confirmar'
        this.confirmButton.classList.add('confirmar')

        this.cancelButton = document.createElement('button')
        this.cancelButton.textContent = 'Cancelar'
        this.cancelButton.classList.add('cancelar')

        const messageElement = document.createElement('h4')
        messageElement.textContent = this.message

        this.container.append(messageElement, this.confirmButton, this.cancelButton)

        this.overlay = document.createElement('div')
        this.overlay.classList.add('dialog-sobreposicao')
        this.overlay.appendChild(this.container)
    }

    handleKeyDown = (event) => {
        if (event.key === 'Escape') {
            this.cancelButton.click()
        }
    }

    close() {
        document.removeEventListener('keydown', this.handleKeyDown)
        this.overlay.remove()
    }

    show() {
        return new Promise((resolve) => {
            this.mountDialog()
            this.overlay.offsetHeight

            requestAnimationFrame(() => {
                document.body.appendChild(this.overlay)
                document.addEventListener('keydown', this.handleKeyDown)

                this.confirmButton.addEventListener('click', () => {
                    this.close()
                    resolve(true)
                }, { once: true })

                this.cancelButton.addEventListener('click', () => {
                    this.close()
                    resolve(false)
                }, { once: true })
            })
        })
    }
}
