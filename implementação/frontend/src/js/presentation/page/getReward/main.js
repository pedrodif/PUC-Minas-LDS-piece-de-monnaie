
import { Url } from '../../../helper/Url.js'
import { Empty } from '../../component/Empty.js'
import { Header } from '../../component/Header.js'
import { Dialog } from './../../component/Dialog.js'
import { Session } from '../../../middleware/Session.js'
import { Service } from '../../../application/Service.js'
import { RewardCard } from '../../component/RewardCard.js'
import { Breadcrumb } from './../../component/Breadcrumb.js'

const header = new Header()
const routes = Url.mountRoutes()
const user = Session.userProvider()

const breadcrumb = Breadcrumb.getBreadcrumb()
const emptyContainer = document.querySelector('#empty-container')
const rewardsContainer = document.querySelector('#rewards-container')

const dialog = new Dialog({
    message: 'Você deseja resgatar este item?',
})

const empty = new Empty({
    figcaption: 'Parece que não temos nenhuma informação por aqui... Mas fique tranquilo: em breve novas vantagens chegarão pra você!',
    margin: '0 0',
    padding: '86px',
    displayTitle: false
})

const rewardService = new Service({
    endpoint: '/api/vantagens',
    toastMessages: {
        create: 'Vantagem resgatada com sucesso!',
    }
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

    const response = await rewardService.create({
        id: data.id,
        resource: 'resgatar'
    })

    if (response?.emissor?.quantidadeMoeda) {
        Session.updateUser({
            ...user,
            quantidadeMoeda: response.emissor.quantidadeMoeda
        })
    }
}

const data = await rewardService.getAll()
if (data.length > 0) {
    emptyContainer.style.display = 'none'

    data.forEach(datum => {
        const rewardCard = new RewardCard({
            item: datum,
            onButtonClick: handleButtonClick
        })
        requestAnimationFrame(() => rewardsContainer.appendChild(rewardCard.render()))
    })
} else {
    requestAnimationFrame(() => emptyContainer.appendChild(empty.render()))
}