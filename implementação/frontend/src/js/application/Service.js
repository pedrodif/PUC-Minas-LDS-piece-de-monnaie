import { APIClient } from './APIClient.js'
import { Notifier } from '../helper/Notifier.js'
import { RequestHelper } from '../helper/RequestHelper.js'

export class Service {
    constructor({
        endpoint,
        toastMessages = {},
        onSuccessDisplayToast = true
    }) {
        this.toastMessages = toastMessages
        this.onSuccessDisplayToast = onSuccessDisplayToast
        this.APIClient = new APIClient(endpoint)
    }

    async perform(action, notifierKey) {
        try {
            const response = await RequestHelper.execute(action)
            const shouldNotify = Object.keys(response).length === 1 || this.onSuccessDisplayToast

            if (shouldNotify)
                Notifier.response(response, notifierKey, this.toastMessages)

            return response
        } catch (error) {
            return { error: error.message }
        }
    }

    async getAll() {
        return this.perform(() => this.APIClient.getAll(), 'list')
    }

    async getById(id) {
        return this.perform(() => this.APIClient.getById(id), 'retrieve')
    }

    async create(data) {
        const { id } = data
        delete data.id

        return this.perform(() => this.APIClient.post(id, data), 'create')
    }

    async update(data) {
        const { id } = data
        return this.perform(() => this.APIClient.put(id, data), 'update')
    }

    async delete(id) {
        return this.perform(() => this.APIClient.delete(id), 'delete')
    }
}