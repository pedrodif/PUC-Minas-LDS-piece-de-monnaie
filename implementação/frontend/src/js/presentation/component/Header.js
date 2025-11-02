import { Url } from '../../helper/Url.js'
import { Session } from '../../middleware/Session.js'

export class Header {
    constructor() {
        this.user = Session.userProvider()
        this.body = document.querySelector('body')
    }

    render() {
        const img = new Image()
        img.src = ''
        img.alt = 'logo'

        const span = document.createElement('span')
        span.textContent = Header.USER_TYPE['aluno']

        const i = document.createElement('i')
        i.classList.add('fa-solid', 'fa-arrow-right-from-bracket')
        i.title = 'Logout'
        i.addEventListener('click', () => {
            Session.kill()
            window.location.href = Url.mountRoutes().login
        })

        const div = document.createElement('div')
        div.append(span, i)

        const header = document.createElement('header')
        header.append(img, div)

        this.body.prepend(header)
    }

    static USER_TYPE = {
        aluno: 'Aluno',
        empresa: 'Empresa',
        professor: 'Professor'
    }
}