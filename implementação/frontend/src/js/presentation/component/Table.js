export class Table {
    constructor(tableElement, pageSize = 3) {
        this.tableElement = tableElement
        this.data = []
        this.columns = []
        this.actions = {}
        this.pageSize = pageSize
        this.currentPage = 1
        this.footer = null
        this.empty = null
    }

    setColumns(columns) {
        this.columns = columns
    }

    setActions(actions) {
        this.actions = {
            ...this.actions,
            ...actions
        }
    }

    getValueByPath(obj, path) {
        return path.split('.').reduce((acc, key) => acc?.[key], obj)
    }

    sortBy(columnName, order = 'asc') {
        this.data.sort((a, b) => {
            const valA = this.getValueByPath(a, columnName)
            const valB = this.getValueByPath(b, columnName)

            if (valA < valB) return order === 'asc' ? -1 : 1
            if (valA > valB) return order === 'asc' ? 1 : -1
            return 0
        })
        this.render(this.data)
    }

    mountTh(columnConfig) {
        const th = document.createElement('th')
        th.textContent = columnConfig.text

        if (columnConfig.sortable) {
            const sortContainer = document.createElement('span')
            sortContainer.style.marginLeft = '5px'
            sortContainer.style.cursor = 'pointer'

            const ascIcon = document.createElement('i')
            ascIcon.classList.add('fa-solid', 'fa-arrow-up')
            ascIcon.title = 'Ordem crescente'
            ascIcon.addEventListener('click', (event) => {
                event.stopPropagation()
                this.sortBy(columnConfig.name, 'asc')
            })

            const descIcon = document.createElement('i')
            descIcon.classList.add('fa-solid', 'fa-arrow-down')
            descIcon.title = 'Ordem decrescente'
            descIcon.addEventListener('click', (event) => {
                event.stopPropagation()
                this.sortBy(columnConfig.name, 'desc')
            })

            sortContainer.appendChild(ascIcon)
            sortContainer.appendChild(descIcon)
            th.appendChild(sortContainer)
        }

        return th
    }

    mountTd(item, columnConfig) {
        const td = document.createElement('td')

        if (columnConfig.name === 'actions') {
            td.classList.add('acoes')
            columnConfig.icons.forEach(actionConfig => {
                td.appendChild(this.mountIcon(actionConfig, item))
            })
            return td
        }

        if (columnConfig.modifier) {
            const value = this.getValueByPath(item, columnConfig.name)
            const customElement = columnConfig.modifier(value, item)
            customElement instanceof HTMLElement
                ? td.appendChild(customElement)
                : td.textContent = customElement
            return td
        }
    }

    mountIcon(actionConfig, item) {
        const i = document.createElement('i')
        i.classList.add('fa-solid', actionConfig.icon)
        i.title = actionConfig.title
        i.dataset.action = actionConfig.name
        i.onclick = async () => {
            const actionResult = await this.actions[actionConfig.name](item)

            if (actionConfig.name === 'onDelete' && actionResult?.result) {
                const { id, key } = actionResult
                this.removeById(id, key)
            }
        }

        return i
    }

    mountFooter() {
        if (!this.footer) {
            this.footer = document.createElement('div')
            this.footer.className = 'tfoot'
            this.tableElement.after(this.footer)
        }

        this.footer.innerHTML = ''
        const totalPages = Math.ceil(this.data.length / this.pageSize)
        if (totalPages <= 1) return

        for (let i = 1; i <= totalPages; i++) {
            const button = document.createElement('button')
            button.textContent = i
            button.className = 'pagina-tabela'
            button.addEventListener('click', () => this.goToPage(i))
            this.footer.appendChild(button)
        }

        const input = document.createElement('input')
        input.type = 'text'
        input.placeholder = 'Ir para...'
        input.value = this.currentPage
        input.name = 'pager'
        input.className = 'pager-input'
        input.addEventListener('change', () => this.goToPage(Number(input.value)))
        this.footer.appendChild(input)
    }

    goToPage(page) {
        const totalPages = Math.ceil(this.data.length / this.pageSize)
        this.currentPage = Math.min(Math.max(1, page), totalPages)
        this.render(this.data)
    }

    addItem(newItem) {
        this.data.push(newItem)
        this.render(this.data)
    }

    updateItem(id, key, updatedItem) {
        this.data = this.data.reduce((acc, item) => item[key] === id ?
            [
                ...acc,
                updatedItem
            ] :
            [
                ...acc,
                item
            ]
            , [])
        this.render(this.data)
    }

    removeById(id, key) {
        this.data = this.data.filter(item => item[key] !== id)
        this.render(this.data)
    }

    mountEmptyState() {
        if (this.empty) {
            this.tableElement.appendChild(this.empty.render())
        }
    }

    render(data = []) {
        this.data = data
        this.tableElement.innerHTML = ''

        if (this.data.length === 0) {
            this.mountEmptyState()
            return
        }

        const start = (this.currentPage - 1) * this.pageSize
        const end = start + this.pageSize
        const pageData = this.data.slice(start, end)

        const thead = document.createElement('thead')
        const headerRow = document.createElement('tr')
        this.columns.forEach(columnConfig => headerRow.appendChild(this.mountTh(columnConfig)))
        thead.appendChild(headerRow)

        const tbody = document.createElement('tbody')
        pageData.forEach((item, index) => {
            const row = document.createElement('tr')
            this.columns.forEach(columnConfig => row.appendChild(this.mountTd(item, columnConfig)))
            row.style.opacity = '0'
            row.style.animation = `fadeInUp 0.5s ease-in-out ${index * 0.1}s forwards`
            tbody.appendChild(row)
        })

        this.tableElement.appendChild(thead)
        this.tableElement.appendChild(tbody)
        this.mountFooter()
    }
}