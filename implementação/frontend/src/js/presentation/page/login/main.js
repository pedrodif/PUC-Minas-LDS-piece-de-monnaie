
import { Form } from '../../component/Form.js'
import { Session } from '../../../middleware/Session.js'
import { Service } from '../../../application/Service.js'

const form = new Form(document.querySelector('form'))
const loginService = new Service({
    endpoint: '/api/alunos',
    toastMessages: {
        create: {
            error: 'Erro ao realizar login.',
            success: 'Login realizado com sucesso!',
        }
    }
})

const handleSubmit = async (data) => {
    const response = await loginService.create(data)
    console.log(response)

    if (response !== false) {
        Session.create(response)
        // window.location.href = ''
    }
}

form.onSubmit(handleSubmit)