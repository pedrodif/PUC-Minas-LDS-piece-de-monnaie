
import { Session } from './../../../middleware/Session.js'
import { Service } from '../../../application/Service.js'
import { RewardCard } from '../../component/RewardCard.js'

const user = Session.userProvider()
const span = document.querySelector('span')
const rewardsContainer = document.querySelector('#rewards-container')
const rewardService = new Service({
    endpoint: ''
})

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
