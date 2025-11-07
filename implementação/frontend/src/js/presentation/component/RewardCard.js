export class RewardCard {
    constructor({ item, onButtonClick }) {
        this.item = item
        this.onButtonClick = onButtonClick
    }

    render() {
        const img = new Image()
        img.src = `data:image/jpg;base64,${this.item.imagem}`
        img.alt = 'reward-option'

        const h3 = document.createElement('h3')
        h3.textContent = this.item.descricao

        const i = document.createElement('i')
        i.classList.add('fa-solid', 'fa-coins')

        const button = document.createElement('button')
        button.textContent = this.item.valor
        button.appendChild(i)

        const div = document.createElement('div')
        div.append(h3, button)

        const article = document.createElement('article')
        article.classList.add('reward-card')
        article.append(img, div)

        return article
    }
}