
import { Form } from '../../component/Form.js'
import { Session } from '../../../middleware/Session.js'
import { Service } from '../../../application/Service.js'

const form = new Form(document.querySelector('form'))
const loginService = new Service({ 
    endpoint: '', 
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
        // window.location.href = ''
    }
}

form.onSubmit(handleSubmit)