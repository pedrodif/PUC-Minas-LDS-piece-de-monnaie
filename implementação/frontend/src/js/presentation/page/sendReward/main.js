import { Url } from '../../../helper/Url.js'
import { Empty } from '../../component/Empty.js'
import { Table } from '../../component/Table.js'
import { Dialog } from '../../component/Dialog.js'
import { Header } from '../../component/Header.js'
import { Session } from '../../../middleware/Session.js'
import { Service } from '../../../application/Service.js'
import { Breadcrumb } from '../../component/Breadcrumb.js'

const rewardService = new Service({
    endpoint: '',
    toastMessages: {
        create: {
            error: 'Erro ao premiar aluno.',
            success: 'Premiação realizada com sucesso!',
        }
    }
})

const studentService = new Service({
    endpoint: '',
    toastMessages: {
        list: {
            error: 'Erro ao listar alunos.',
            success: 'Alunos listadas com sucesso!',
        },
    }
})

const routes = Url.mountRoutes()
const user = Session.userProvider()
const breadcrumb = Breadcrumb.getBreadcrumb()

const header = new Header()
const table = new Table(document.querySelector('table'))
const dialog = new Dialog('Deseja premiar este aluno?')

header.render()
breadcrumb.add([
    {
        label: 'Home',
        link: routes.home
    }
])

table.setColumns([
    {
        name: 'nome',
        text: 'Nome',
        sortable: true,
        modifier: (value) => value
    },
    {
        name: 'cpf',
        text: 'CPF',
        sortable: true,
        modifier: (value) => value
    },
    {
        name: 'rg',
        text: 'RG',
        sortable: true,
        modifier: (value) => value
    },
    {
        name: 'email',
        text: 'Email',
        sortable: true,
        modifier: (value) => value
    },
    {
        name: 'value',
        text: 'Valor de Premiação',
        sortable: false,
        modifier: (_, data) => {
            const input = document.createElement('input')
            input.type = 'number'
            input.value = 1
            input.min = 1
            input.step = 1
            input.pattern = '\\d+'
            input.style.width = '80px'
            input.style.padding = '8px'
            input.style.borderRadius = 'var(--radius)'
            input.style.border = '1px solid var(--gray-mid)'
            input.name = 'quantity-input'

            input.addEventListener('input', () => {
                input.value = input.value.replace(/\D/g, '')
                if (input.value === '' || Number(input.value) <= 0) input.value = 1
            })

            return input
        }
    },
    {
        name: 'confirmButton',
        text: 'Confirmação',
        sortable: false,
        modifier: (_, data) => {
            const button = document.createElement('button')
            button.textContent = 'Teste'
            button.onclick = () => console.log(data.value)
            return button
        }
    },
])

table.empty = new Empty({
    figcaption: 'Parece que não temos nenhuma informação por aqui... Novos alunos aparecerão automaticamente nesta tabela quando forem adicionados.',
    margin: '0 0',
    padding: '86px',
    displayTitle: false
})

// const data = await companyService.getAll()
table.render([
    {
        nome: "Ana Souza",
        cpf: "123.456.789-00",
        rg: "12.345.678-9",
        email: "ana.souza@example.com",
    },
    {
        nome: "Bruno Almeida",
        cpf: "987.654.321-00",
        rg: "98.765.432-1",
        email: "bruno.almeida@example.com",
    },
    {
        nome: "Carla Ribeiro",
        cpf: "456.789.123-00",
        rg: "45.678.912-3",
        email: "carla.ribeiro@example.com",
    },
    {
        nome: "Diego Martins",
        cpf: "321.654.987-00",
        rg: "32.165.498-7",
        email: "diego.martins@example.com",
    }
])