
import { Url } from '../../../helper/Url.js'
import { Session } from './../../../middleware/Session.js'

const routes = Url.mountRoutes()
const user = Session.userProvider()
const span = document.querySelector('span')
const p = document.querySelector('p')
const button = document.querySelector('button')
const figcaption = document.querySelector('figcaption')

const USER_TYPE = {
    aluno: 'Aluno',
    empresa: 'Empresa',
    professor: 'Professor'
}

const BUTTON_CONFIGS = {
    aluno: {
        text: 'Resgatar',
        displayBalance: 'block',
        onClick: () => window.location.href = routes.getReward
    },
    empresa: {
        text: 'Vantagens',
        displayBalance: 'none',
        onClick: () => window.location.href = ''
    },
    professor: {
        text: 'Meus Alunos',
        displayBalance: 'block',
        onClick: () => window.location.href = ''
    }
}

span.textContent = USER_TYPE['aluno']
figcaption.textContent  = `Seja bem vindo(a) ao Pièce de Monnaie ${user.nome}`
p.style.display = BUTTON_CONFIGS['aluno'].displayBalance
button.textContent = BUTTON_CONFIGS['aluno'].text
button.onclick = BUTTON_CONFIGS['aluno'].onClick