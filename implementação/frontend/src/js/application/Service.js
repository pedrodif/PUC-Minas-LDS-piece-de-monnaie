import { APIClient } from './APIClient.js'
import { RequestHelper } from '../helper/RequestHelper.js'


export class Service {
    constructor(endpoint) {
        this.APIClient = new APIClient(endpoint)
    }

    async getAll() {
        const response = await RequestHelper.execute(() => this.APIClient.getAll())
        return response
    }

    async getById(id) {
        try {
            const response = await RequestHelper.execute(() => this.APIClient.getById(id))
            return response
        } catch (error) {
            return { error: error.message }
        }
    }

    async create(data) {
        try {
            const response = await RequestHelper.execute(() => this.APIClient.post(data))
            return response
        } catch (err) {
            return { error: err.message }
        }
    }

    async update(data) {
        const { id } = data

        try {
            const response = await RequestHelper.execute(() => this.APIClient.put(id, data))
            return response
        } catch (err) {
            return { error: err.message }
        }
    }

    async delete(id) {
        try {
            const response = await RequestHelper.execute(() => this.APIClient.delete(id))
            return response
        } catch (error) {
            return { error: error.message }
        }
    }
}