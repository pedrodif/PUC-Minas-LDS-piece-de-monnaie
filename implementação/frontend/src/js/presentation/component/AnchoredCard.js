export class AnchoredCard {
    constructor(item) {
        this.item = item
    }

    render() {
        const i = document.createElement('i')
        i.classList.add(...this.item.icone)

        const span = document.createElement('span')
        span.textContent = this.item.titulo

        const p = document.createElement('p')
        p.textContent = this.item.descricao

        const anchor = document.createElement('a')
        anchor.classList.add('ancora-card')
        anchor.href = this.item.href

        anchor.append(i, span, p)

        return anchor
    }
}
