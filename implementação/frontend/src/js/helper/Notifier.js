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

    static response(response, variant, options) {
        if (Object.keys(response).length === 1) {
            if (response?.error) {
                Notifier.error(response.error)
                return
            }

            if (response?.rg) {
                Notifier.error(response.rg)
                return
            }

            if (response?.cpf) {
                Notifier.error(response.cpf)
                return
            }

            if (response?.cnpj) {
                Notifier.error(response.cnpj)
                return
            }
        }

        if (variant != 'list' && variant != 'retrieve') {
            Notifier.success(options[variant])
        }
    }
}