export class Dialog {
    constructor({ message, displayTextarea = false }) {
        this.message = message
        this.displayTextarea = displayTextarea
    }

    mount() {
        this.container = document.createElement('section')
        this.container.classList.add('dialog-container')
        this.container.setAttribute('role', 'dialog')
        this.container.setAttribute('aria-modal', 'true')
        this.container.setAttribute('aria-labelledby', 'dialog-title')

        const messageElement = document.createElement('h4')
        messageElement.textContent = this.message

        this.confirmButton = document.createElement('button')
        this.confirmButton.textContent = 'Confirmar'
        this.confirmButton.classList.add('confirmar')

        this.cancelButton = document.createElement('button')
        this.cancelButton.textContent = 'Cancelar'
        this.cancelButton.classList.add('cancelar')

        this.buttonContainer = document.createElement('div')
        this.buttonContainer.append(this.confirmButton, this.cancelButton)

        this.container.append(messageElement, this.buttonContainer)

        if (this.displayTextarea) {
            this.textarea = document.createElement('textarea')
            this.textarea.name = 'reward-message'
            this.textarea.placeholder = 'Adicione uma mensagem de reconhecimento ao aluno…'
            this.container.insertBefore(this.textarea, this.buttonContainer)
        }

        this.overlay = document.createElement('div')
        this.overlay.classList.add('dialog-sobreposicao')
        this.overlay.appendChild(this.container)
    }

    getData() {
        return this.textarea.value
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
            this.mount()
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
