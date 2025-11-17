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
        name: 'descricao',
        text: 'Descricão',
        sortable: true,
        modifier: (value) => value
    },
    {
        name: 'valor',
        text: 'Valor',
        sortable: true,
        modifier: (value) => value
    },
    {
        name: 'actions',
        text: 'Ações',
        sortable: false,
        icons: [
            { name: 'onDetails', icon: 'fa-magnifying-glass', title: 'Detalhes' },
            { name: 'onDelete', icon: 'fa-times', title: 'Deletar' }
        ]
    }
])

table.empty = new Empty({
    figcaption: 'Parece que não temos nenhuma informação por aqui... Novos alunos aparecerão automaticamente nesta tabela quando forem adicionados.',
    margin: '0 0',
    padding: '86px',
    displayTitle: false
})

// const data = await companyService.getAll()
// table.render(data)