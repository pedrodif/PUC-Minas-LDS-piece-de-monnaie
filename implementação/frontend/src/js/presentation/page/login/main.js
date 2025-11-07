
import { Form } from '../../component/Form.js'
import { Session } from '../../../middleware/Session.js'
import { Service } from '../../../application/Service.js'
import { Url } from '../../../helper/Url.js'

const routes = Url.mountRoutes()
const form = new Form(document.querySelector('form'))
const loginService = new Service({
    endpoint: '/api/auth/login',
    toastMessages: {
        create: {
            error: 'Erro ao realizar login.'
        }
    }
})

const handleSubmit = async (data) => {
    const response = await loginService.create(data)

    if (response !== false) {
        Session.create(response)
        window.location.href = routes.home
    }
}

form.onSubmit(handleSubmit)