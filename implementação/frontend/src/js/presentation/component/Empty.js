export class Empty {
    constructor({
        figcaption,
        padding = '96px',
        margin = '16px 0',
        displayTitle = true,
        boxShadow = 'var(--shadow-default)',
        backgroundColor = 'var(--gray-lin)',
        fontSize = {
            h2: {
                desktop: '20px',
                mobile: '16px'
            },
            figcaption: {
                desktop: '18px',
                mobile: '14px'
            }
        }
    }) {
        this.margin = margin
        this.padding = padding
        this.fontSize = fontSize
        this.boxShadow = boxShadow
        this.figcaption = figcaption
        this.displayTitle = displayTitle
        this.backgroundColor = backgroundColor

        this.empty = document.createElement('figure')
        this.mediaQuery = window.matchMedia('(min-width: 758px)')
        this.mediaQuery.onchange = this.handleScreenChange
    }

    handleScreenChange = () => {
        const elements = ['h2', 'figcaption'].reduce((acc, item) => [
            ...acc,
            this.empty.querySelector(item)
        ], [])

        elements.forEach(element => {
            if (element) {
                const config = this.fontSize[element.tagName.toLowerCase()]
                element.style.fontSize = this.mediaQuery.matches
                    ? config.desktop
                    : config.mobile
            }
        })
    }

    render() {
        this.empty.innerHTML = ''

        const h2 = document.createElement('h2')
        h2.textContent = 'Vamos Começar?'

        const figcaption = document.createElement('figcaption')
        figcaption.textContent = this.figcaption

        const div = document.createElement('div')
        if (this.displayTitle) {
            div.appendChild(h2)
        }
        div.appendChild(figcaption)

        this.empty.classList.add('empty-state')
        this.empty.appendChild(div)

        this.empty.style.margin = this.margin
        this.empty.style.padding = this.padding
        this.empty.style.boxShadow = this.boxShadow
        this.empty.style.setProperty('background-color', this.backgroundColor, 'important')

        this.handleScreenChange()
        return this.empty
    }
}