
import { Url } from '../../../helper/Url.js'
import { Header } from '../../component/Header.js'
import { Service } from '../../../application/Service.js'
import { RewardCard } from '../../component/RewardCard.js'
import { Breadcrumb } from './../../component/Breadcrumb.js'

const header = new Header()
const routes = Url.mountRoutes()
const breadcrumb = Breadcrumb.getBreadcrumb()
const rewardsContainer = document.querySelector('#rewards-container')
const rewardService = new Service({
    endpoint: ''
})

header.render()
breadcrumb.add([
    {
        label: 'Home',
        link: routes.home
    }
])

const data = rewardService.getAll()
data.forEach(datum => {
    const rewardCard = new RewardCard({ item: datum })
    requestAnimationFrame(() => rewardsContainer.appendChild(rewardCard.render()))
})