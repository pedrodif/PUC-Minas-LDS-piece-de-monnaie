
import { Url } from '../../../helper/Url.js'
import { Empty } from '../../component/Empty.js'
import { Header } from '../../component/Header.js'
import { Service } from '../../../application/Service.js'
import { RewardCard } from '../../component/RewardCard.js'
import { Breadcrumb } from './../../component/Breadcrumb.js'

const header = new Header()
const routes = Url.mountRoutes()
const breadcrumb = Breadcrumb.getBreadcrumb()
const rewardsContainer = document.querySelector('#rewards-container')
const emptyContainer = document.querySelector('#empty-container')

const empty = new Empty({
    figcaption: 'Parece que não temos nenhuma informação por aqui... Mas fique tranquilo: em breve novas vantagens chegarão pra você!',
    margin: '0 0',
    padding: '86px',
    displayTitle: false
})

const rewardService = new Service({
    endpoint: '/api/vantagens'
})

header.render()
breadcrumb.add([
    {
        label: 'Home',
        link: routes.home
    }
])

const handleButtonClick = (data) => {
    console.log(data)
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