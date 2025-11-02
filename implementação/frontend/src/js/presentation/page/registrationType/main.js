import { Breadcrumb } from './../../component/Breadcrumb.js'
import { AnchoredCard } from '../../component/AnchoredCard.js'
import { Url } from '../../../helper/Url.js'

const routes = Url.mountRoutes()
const breadcrumb = Breadcrumb.getBreadcrumb()
const cardsContainer = document.querySelector('#cards-container')
const CARDS = [
    {
        id: 1,
        titulo: 'Sou Aluno',
        // href: rotas.usuarioCNPJ,
        icone: ['fa-solid', 'fa-graduation-cap'],
        descricao: 'Cadastro de aluno para acesso ao Pièce'
    },
    {
        id: 2,
        titulo: 'Sou Empresa',
        // href: rotas.usuarioCPF,
        icone: ['fa-solid', 'fa-briefcase'],
        descricao: 'Cadastro de empresa para acesso ao Pièce'
    },
]

breadcrumb.add([
    {
        label: 'Login',
        link: routes.login
    }
])

CARDS.forEach(datum => {
    const anchoredCard = new AnchoredCard(datum)
    requestAnimationFrame(() => cardsContainer.appendChild(anchoredCard.render()))
})