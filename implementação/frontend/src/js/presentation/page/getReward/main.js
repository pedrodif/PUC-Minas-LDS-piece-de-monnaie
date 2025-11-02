
import { Url } from '../../../helper/Url.js'
import { Service } from '../../../application/Service.js'
import { Session } from './../../../middleware/Session.js'
import { RewardCard } from '../../component/RewardCard.js'
import { Breadcrumb } from './../../component/Breadcrumb.js'

const routes = Url.mountRoutes()
const user = Session.userProvider()
const span = document.querySelector('span')
const breadcrumb = Breadcrumb.getBreadcrumb()
const rewardsContainer = document.querySelector('#rewards-container')
const rewardService = new Service({
    endpoint: ''
})

breadcrumb.add([
    {
        label: 'Home',
        link: routes.home
    }
])

const USER_TYPE = {
    aluno: 'Aluno',
    empresa: 'Empresa',
    professor: 'Professor'
}

span.textContent = USER_TYPE['aluno']
const data = rewardService.getAll()

data.forEach(datum => {
    const rewardCard = new RewardCard({ item: datum })
    requestAnimationFrame(() => rewardsContainer.appendChild(rewardCard.render()))
})
