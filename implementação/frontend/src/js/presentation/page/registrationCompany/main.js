import { Url } from '../../../helper/Url.js'
import { Form } from '../../component/Form.js'
import { Service } from '../../../application/Service.js'
import { Breadcrumb } from './../../component/Breadcrumb.js'

const routes = Url.mountRoutes()
const breadcrumb = Breadcrumb.getBreadcrumb()
const form = new Form(document.querySelector('form'))
const CNPJInput = document.querySelector('input[name="cnpj"]')
const passwordInput = document.querySelector('input[type="password"]')

const companyService = new Service({
    endpoint: '/api/auth/parceiro',
    toastMessages: {
        create: 'Nova empresa cadastrada com sucesso!'
    }
})

breadcrumb.add([
    {
        label: 'Login',
        link: routes.login
    },
    {
        label: 'Cadastre-se',
        link: routes.registrationType
    }
])

const handleSubmit = async (data) => {
    await companyService.create(data)
}

form.onSubmit(handleSubmit)

IMask(CNPJInput, {
    mask: '00.000.000/0000-00'
})

passwordInput.addEventListener('focus', (event) => {
    event.target.type = 'text'
})

passwordInput.addEventListener('blur', (event) => {
    event.target.type = 'password'
})