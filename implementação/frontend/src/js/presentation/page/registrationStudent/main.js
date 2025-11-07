import { Url } from '../../../helper/Url.js'
import { Form } from '../../component/Form.js'
import { Service } from '../../../application/Service.js'
import { Breadcrumb } from './../../component/Breadcrumb.js'

const routes = Url.mountRoutes()
const breadcrumb = Breadcrumb.getBreadcrumb()
const form = new Form(document.querySelector('form'))
const RGInput = document.querySelector('input[name="rg"]')
const CPFInput = document.querySelector('input[name="cpf"]')
const cursoSelect = document.querySelector('select[name="cursoId"]')
const passwordInput = document.querySelector('input[type="password"]')

const cursoService = new Service({ endpoint: '/api/cursos' })
const alunoService = new Service({
    endpoint: '/api/auth/aluno',
    toastMessages: {
        create: {
            error: 'Erro ao cadastrar novo aluno.',
            success: 'Novo aluno cadastrado com sucesso!'
        }
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

const cursos = await cursoService.getAll()

const options = cursos.reduce((acc, item) => acc +=
    `<option value="${item.id}">${item.nome} - ${item.instituicaoDeEnsino.nome}</option>`
    , `<option value="" disabled selected>Curso/Instituição</option>`)

cursoSelect.innerHTML = options

IMask(CPFInput, {
    mask: '000.000.000-00'
})

IMask(RGInput, {
    mask: '00.000.000-0'
})

passwordInput.addEventListener('focus', (event) => {
    event.target.type = 'text'
})

passwordInput.addEventListener('blur', (event) => {
    event.target.type = 'password'
})

const handleSubmit = async (data) => {
    await alunoService.create(data)
}
form.onSubmit(handleSubmit)