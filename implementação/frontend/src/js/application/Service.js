import { APIClient } from './APIClient.js'
import { Notifier } from '../helper/Notifier.js'
import { RequestHelper } from '../helper/RequestHelper.js'

export class Service {
    constructor({ endpoint, toastMessages }) {
        this.toastMessages = toastMessages
        this.APIClient = new APIClient(endpoint)
    }

    async getAll() {
        const response = await RequestHelper.execute(() => this.APIClient.getAll())
        Notifier.response(response, 'list', this.toastMessages)

        return response
    }

    async getById(id) {
        try {
            const response = await RequestHelper.execute(() => this.APIClient.getById(id))
            Notifier.response(response, 'retrieve', this.toastMessages)

            return response
        } catch (error) {
            return { error: error.message }
        }
    }

    async create(data) {
        try {
            const response = await RequestHelper.execute(() => this.APIClient.post(data))
            Notifier.response(response, 'create', this.toastMessages)

            return response
        } catch (err) {
            return { error: err.message }
        }
    }

    async update(data) {
        const { id } = data

        try {
            const response = await RequestHelper.execute(() => this.APIClient.put(id, data))
            Notifier.response(response, 'update', this.toastMessages)

            return response
        } catch (err) {
            return { error: err.message }
        }
    }

    async delete(id) {
        try {
            const response = await RequestHelper.execute(() => this.APIClient.delete(id))
            Notifier.response(response, 'delete', this.toastMessages)

            return response
        } catch (error) {
            return { error: error.message }
        }
    }
}