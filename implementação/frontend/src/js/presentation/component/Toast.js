export class Toast {
    static INSTANCE = null

    constructor() {
        if (Toast.INSTANCE) {
            throw new Error("Use Toast.getToast() para acessar a instância.")
        }

        this.queue = []
        this.displaying = false
        this.container = this.mountContainer()
    }

    mountContainer() {
        const container = document.createElement('div')
        container.id = 'toast-container'
        container.className = 'toast-container'
        document.body.appendChild(container)

        return container
    }

    mountToast(message, type) {
        const toastTypes = {
            info: 'fa-circle-info', 
            success: 'fa-circle-check',
            error: 'fa-circle-xmark',
            warning: 'fa-triangle-exclamation'
        }

        const icon = document.createElement('i')
        icon.classList.add('fa-solid', toastTypes[type])

        const deleteIcon = document.createElement('i')
        deleteIcon.classList.add('fa-solid', 'fa-times', 'icone-deletar')
        deleteIcon.onclick = () => deleteIcon.closest('div').remove()
        
        const description = document.createElement('p')
        description.textContent = message

        const toast = document.createElement('div')
        toast.setAttribute('role', 'alert')
        toast.className = `toast ${type}`

        toast.append(icon, description, deleteIcon)
        return toast
    }

    show(message, type) {
        const toast = this.mountToast(message, type)
        this.queue.push(toast)

        if (!this.displaying) {
            this.displaying = true
            this.next()
        }
    }

    next() {
        if (this.queue.length === 0) {
            this.displaying = false
            return
        }

        const toast = this.queue.shift()
        this.container.appendChild(toast)

        setTimeout(() => {
            toast.remove()
            this.next()
        }, 2000)
    }

    static getToast() {
        if (!Toast.INSTANCE) {
            Toast.INSTANCE = new Toast()
        }

        return Toast.INSTANCE
    }
}
