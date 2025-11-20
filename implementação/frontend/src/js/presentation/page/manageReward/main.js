import { Url } from '../../../helper/Url.js'
import { Form } from '../../component/Form.js'
import { Table } from '../../component/Table.js'
import { Modal } from '../../component/Modal.js'
import { Empty } from '../../component/Empty.js'
import { Header } from '../../component/Header.js'
import { Dialog } from '../../component/Dialog.js'
import { Session } from '../../../middleware/Session.js'
import { Service } from '../../../application/Service.js'
import { Breadcrumb } from '../../component/Breadcrumb.js'
import { Utilities } from '../../../helper/Utilities.js'

const routes = Url.mountRoutes()
const user = Session.userProvider()
const breadcrumb = Breadcrumb.getBreadcrumb()
const openModalButton = document.querySelector('.abrir')

const header = new Header()
const form = new Form(document.querySelector('form'))
const table = new Table(document.querySelector('table'))
const modal = new Modal(document.querySelector('#modal'))
const dialog = new Dialog({ message: 'Deseja deletar essa vantagem?' })

let retrievedData = null
let originalBase64Image = null

FilePond.registerPlugin(FilePondPluginFileEncode)
const fileUploader = FilePond.create(document.querySelector('.filepond'), {
    allowMultiple: false,
    acceptedFileTypes: ['image/jpg'],
    labelIdle: `Arraste e solte sua imagem aqui ou <span class="filepond--label-action">buscar</span>`,
    labelFileProcessing: 'Enviando arquivo...',
    labelFileLoadError: 'Erro ao carregar o arquivo.',
    labelFileTypeNotAllowed: 'Tipo de arquivo não permitido.',
    labelTapToCancel: 'Toque para cancelar.',
    allowFileEncode: true
})

const companyService = new Service({
    endpoint: `/api/empresas-parceiras/${user.id}/vantagens`,
    toastMessages: {
        create: {
            error: 'Erro ao cadastrar vantagem.',
            success: 'Vantagem cadastrada com sucesso!',
        }
    }
})

const rewardService = new Service({
    endpoint: '/api/vantagens',
    toastMessages: {
        delete: {
            error: 'Erro ao deletar vantagem.',
            success: 'Vantagem deletada com sucesso!',
        },
        update: {
            error: 'Erro ao atualizar vantagem.',
            success: 'Vantagem atualizada com sucesso!',
        },
    }
})

header.render()
breadcrumb.add([
    {
        label: 'Home',
        link: routes.home
    }
])

const openModal = ({ create = true } = {}) => {
    modal.open(create)
}

const fillForm = (data = {}) => {
    requestAnimationFrame(() => form.setInitialValues(data))
}

const handleSubmit = async (data) => {
    const files = fileUploader.getFiles()
    const base64 = files[0].getFileEncodeBase64String()
    const fileName = files[0].file.name

    let response
    const { modo } = Url.getParams()

    if (modo === 'detalhes') {
        response = await rewardService.update({
            ...retrievedData,
            ...data,
            imagem: originalBase64Image
        })
    } else {
        response = await companyService.create({
            ...data,
            imagem: base64,
            nomeImagem: fileName
        })
    }

    modal.close()
    if (response?.error || response === false) {
        return
    }

    if (modo === 'criar') {
        requestAnimationFrame(() => table.addItem(response))
    }

    if (modo === 'detalhes') {
        const { id } = response
        requestAnimationFrame(() => table.updateItem(id, 'id', response))
    }
}

form.onSubmit(handleSubmit)
modal.addOpeningObserver(() => Url.addQueryParam('modo', 'criar'))
modal.addClosingObserver(() => Url.removeQueryParam('modo'))
modal.addClosingObserver(() => fileUploader.removeFiles())
modal.addClosingObserver(fillForm)

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

table.setActions({
    onDetails: (data) => {
        retrievedData = data
        originalBase64Image = data.imagem

        Url.addQueryParam('modo', 'detalhes')
        fillForm({
            ...data,
            imagem: ''
        })

        const blob = Utilities.base64ToFile(data.imagem, data.nomeImagem)
        fileUploader.removeFiles()
        fileUploader.addFile(blob)

        openModal({ create: false })
    },
    onDelete: async (data) => {
        const { id } = data

        const confirmed = await dialog.show()
        if (!confirmed) return { result: confirmed }

        const deleted = await rewardService.delete(id)
        return { id, key: 'id', result: !!deleted }
    }
})

table.empty = new Empty({
    figcaption: 'Parece que não temos nenhuma informação por aqui... Que tal cadastrar uma nova vantagem?',
    margin: '0 0',
    padding: '86px'
})

const data = await companyService.getAll()
table.render(data)

openModalButton.addEventListener('click', openModal)