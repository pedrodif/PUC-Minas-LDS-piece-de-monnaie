import { Toast } from '../presentation/component/Toast.js'

export class Notifier {
    static success(message) {
        Toast.getToast().show(message, 'success')
    }

    static error(message) {
        Toast.getToast().show(message, 'error')
    }

    static warn(message) {
        Toast.getToast().show(message, 'warn')
    }

    static info(message) {
        Toast.getToast().show(message, 'info')
    }

    static response() {
        throw new Error('Método requestResponse deve ser implementado nas classes filhas.')
    }

    static response(response, variant, options) {
        if (response?.error) {
            Notifier.error(response.error)
            return
        }

        if (!response) {
            Notifier.error(options[variant].error)
            return
        }

        if (variant != 'list' && variant != 'retrieve') {
            Notifier.success(options[variant].success)
        }
    }
}