import { Session } from "../middleware/Session.js"

export class APIClient {
    constructor(endpoint) {
        this.token = Session.tokenProvider().value

        this.HEADERS_DEFAULT = {
            headers: {
                'Accept': 'application/json',
                ...(this.token ? { 'Authorization': `Bearer ${this.token}` } : {})
            }
        }

        this.HEADERS_JSON = {
            headers: {
                ...this.HEADERS_DEFAULT.headers,
                'Content-Type': 'application/json',
            }
        }

        this.url_base = `http://localhost:8080${endpoint}`
    }

    getAll() {
        return fetch(this.url_base, this.HEADERS_DEFAULT)
            .then(response => response.json())
            .catch(error => console.error('getAll: ', error))
    }

    getById(id) {
        if (!id) {
            throw new Error('O ID é obrigatório.')
        }

        return fetch(`${this.url_base}/${id}`, this.HEADERS_DEFAULT)
            .then(response => response.json())
            .catch(error => console.error('getById: ', error))
    }

    post(id, data) {
        if (!data) {
            throw new Error('É necessário fornecer os dados para envio.')
        }

        return fetch(`${this.url_base}${id ? `/${id}` : ''}`,
            {
                method: 'POST',
                ...this.HEADERS_JSON,
                body: JSON.stringify(data)
            }
        ).then(response => response.json())
            .catch(error => console.error('post: ', error))
    }

    put(id, data) {
        if (!id) {
            throw new Error('O ID é obrigatório.')
        }

        if (!data) {
            throw new Error('É necessário fornecer os dados para envio.')
        }

        return fetch(`${this.url_base}/${id}`,
            {
                method: 'PUT',
                ...this.HEADERS_JSON,
                body: JSON.stringify(data)
            }
        ).then(response => response.json())
            .catch(error => console.error('put: ', error))
    }

    delete(id) {
        if (!id) {
            throw new Error('O ID é obrigatório.')
        }

        return fetch(`${this.url_base}/${id}`,
            {
                method: 'DELETE',
                ...this.HEADERS_DEFAULT
            }
        ).then(response => response.json())
            .catch(error => console.error('delete: ', error))
    }
}