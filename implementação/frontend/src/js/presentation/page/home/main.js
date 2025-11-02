
import { Url } from '../../../helper/Url.js'
import { Header } from '../../component/Header.js'
import { Session } from './../../../middleware/Session.js'

const header = new Header()
const routes = Url.mountRoutes()
const user = Session.userProvider()
const p = document.querySelector('p')
const button = document.querySelector('button')
const figcaption = document.querySelector('figcaption')

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

const customizeEnvironment = () => {
    button.textContent = BUTTON_CONFIGS['aluno'].text
    button.onclick = BUTTON_CONFIGS['aluno'].onClick
    p.style.display = BUTTON_CONFIGS['aluno'].displayBalance
    figcaption.textContent = `Seja bem vindo(a) ao Pièce de Monnaie ${user.nome}`
}

header.render()
customizeEnvironment()