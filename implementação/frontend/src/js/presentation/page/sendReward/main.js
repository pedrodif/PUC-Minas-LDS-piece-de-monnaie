import { Url } from '../../../helper/Url.js'
import { Empty } from '../../component/Empty.js'
import { Table } from '../../component/Table.js'
import { Dialog } from '../../component/Dialog.js'
import { Header } from '../../component/Header.js'
import { Session } from '../../../middleware/Session.js'
import { Service } from '../../../application/Service.js'
import { Breadcrumb } from '../../component/Breadcrumb.js'

const routes = Url.mountRoutes()
const user = Session.userProvider()
const breadcrumb = Breadcrumb.getBreadcrumb()

const header = new Header()
const table = new Table(document.querySelector('table'))

const dialog = new Dialog({
    message: 'Confirmar a premiação deste aluno?',
    displayTextarea: true
})

const rewardService = new Service({
    endpoint: `/api/professores/enviar-moedas`,
    toastMessages: {
        create: {
            error: 'Erro ao premiar aluno.',
            success: 'Premiação realizada com sucesso!',
        }
    }
})

const studentService = new Service({
    endpoint: `/api/alunos/professor/${user.id}`,
})

header.render()
breadcrumb.add([
    {
        label: 'Home',
        link: routes.home
    }
])

const handleButtonClick = async (data) => {
    const confirmed = await dialog.show()
    if (!confirmed) return

    const input = document.querySelector(`input[data-student-id="${data.id}"]`)
    const reward = input.value
    const message = dialog.getData()

    const response = await rewardService.create({
        id: data.id,
        montante: reward,
        mensagem: message === '' ? 'Parabéns pelo seu esforço! Continue assim.' : message
    })

    if (response?.emissor?.quantidadeMoeda) {
        Session.updateUser({
            ...user,
            quantidadeMoeda: response.emissor.quantidadeMoeda
        })
    }

    input.value = 1
}

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
            input.name = 'quantity-input'
            input.value = 1
            input.min = 1
            input.step = 1
            input.pattern = '\\d+'
            input.style.width = '80px'
            input.style.padding = '8px'
            input.style.borderRadius = 'var(--radius)'
            input.style.border = '1px solid var(--gray-mid)'
            input.dataset.studentId = data.id

            input.addEventListener('input', () => {
                input.value = input.value.replace(/\D/g, '')
                if (input.value === '' || Number(input.value) <= 0) input.value = 1
            })

            return input
        }
    },
    {
        name: 'deliberation',
        text: 'Deliberação',
        sortable: false,
        modifier: (_, data) => {
            const button = document.createElement('button')
            button.type = 'button'
            button.textContent = 'Confirmar'
            button.addEventListener('click', () => handleButtonClick(data))
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

const data = await studentService.getAll()
table.render(data === false ? [] : data)
