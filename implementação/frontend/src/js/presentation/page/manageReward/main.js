import { Url } from '../../../helper/Url.js'
import { Form } from '../../component/Form.js'
import { Table } from '../../component/Table.js'
import { Modal } from '../../component/Modal.js'
import { Empty } from '../../component/Empty.js'
import { Header } from '../../component/Header.js'
import { Dialog } from '../../component/Dialog.js'
import { Service } from '../../../application/Service.js'
import { Breadcrumb } from '../../component/Breadcrumb.js'

const routes = Url.mountRoutes()
const breadcrumb = Breadcrumb.getBreadcrumb()
const openModalButton = document.querySelector('.abrir')

const header = new Header()
const form = new Form(document.querySelector('form'))
const table = new Table(document.querySelector('table'))
const modal = new Modal(document.querySelector('#modal'))
const dialog = new Dialog('Deseja deletar essa vantagem?')

FilePond.registerPlugin(FilePondPluginFileEncode)
const fileUploader = FilePond.create(document.querySelector('.filepond'), {
    allowMultiple: false,
    acceptedFileTypes: ['image/*'],
    labelIdle: `Arraste e solte sua imagem aqui ou <span class="filepond--label-action">buscar</span>`,
    labelFileProcessing: 'Enviando arquivo...',
    labelFileLoadError: 'Erro ao carregar o arquivo.',
    labelFileTypeNotAllowed: 'Tipo de arquivo não permitido.',
    labelTapToCancel: 'Toque para cancelar.',
    allowFileEncode: true
})

const rewardService = new Service({
    endpoint: '',
    toastMessages: {
        create: {
            error: 'Erro ao cadastrar vantagem.',
            success: 'Vantagem cadastrada com sucesso!',
        },
        delete: {
            error: 'Erro ao deletar vantagem.',
            success: 'Vantagem deletada com sucesso!',
        },
        update: {
            error: 'Erro ao atualizar vantagem.',
            success: 'Vantagem atualizada com sucesso!',
        },
        list: {
            error: 'Erro ao listar vantagens.',
            success: 'Vantagens listadas com sucesso!',
        },
        retrieve: {
            error: 'Erro ao recuperar vantagem.',
            success: 'Vantagem recuperada com sucesso!',
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

const handleSubmit = async (retrievedData) => {
    const { descricao, valor } = retrievedData

    const files = fileUploader.getFiles()
    const base64 = files.length > 0 ? files[0].getFileEncodeBase64String() : null

    const data = {
        descricao,
        valor,
        imagem: base64
    }

    const { modo } = Url.getParams()
    const response = modo === 'detalhes'
        ? await rewardService.update(data)
        : await rewardService.create(data)


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
        Url.addQueryParam('modo', 'detalhes')

        fillForm(data)
        fileUploader.removeFiles()
        fileUploader.addFile(data.imagem, {
            type: 'local'
        })

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

const data = rewardService.getAll()
table.render(data)

openModalButton.addEventListener('click', openModal)