export class Form {
    constructor(formElement) {
        this.formElement = formElement
    }

    getData() {
        const formData = new FormData(this.formElement)
        const retrievedData = Object.fromEntries(formData.entries())

        for (let key in retrievedData) {
            retrievedData[key] = retrievedData[key].trim()
        }

        return retrievedData
    }

    onSubmit(callback) {
        this.formElement.removeEventListener('submit', this.handleSubmit)

        this.handleSubmit = async (event) => {
            event.preventDefault()
            const retrievedData = this.getData()
            await callback(retrievedData)
        }

        this.formElement.addEventListener('submit', this.handleSubmit)
    }

    debounce(method, delay) {
        let timeoutId
        return (...args) => {
            clearTimeout(timeoutId)
            timeoutId = setTimeout(() => { method.apply(null, args) }, delay)
        }
    }

    onInput(element, callback) {
        if (!element) return

        this.debouncedCallback = this.debounce(async (value) => {
            if (value === '') return
            if (value.length < 9) return
            await callback(value)

        }, 1200)

        element.addEventListener('input', (event) => {
            this.debouncedCallback(event.target.value.trim())
        })
    }

    setInitialValues(initialValues = {}) {
        const fields = this.formElement.querySelectorAll('input, select, textarea')

        fields.forEach(field => {
            const value = initialValues[field.name]

            if (value !== undefined) {
                switch (field.type) {
                    case 'checkbox':
                        if (Array.isArray(value)) {
                            field.checked = value.includes(field.value)
                        } else {
                            field.checked = field.value === String(value)
                        }
                        break

                    case 'radio':
                        field.checked = field.value === String(value)
                        break

                    case 'select-one':
                        const option = [...field.options].find(opt => opt.value === String(value))
                        if (option) option.selected = true
                        break

                    default:
                        field.value = value
                }
            } else {
                switch (field.type) {
                    case 'checkbox':
                    case 'radio':
                        field.checked = false
                        break
                    case 'select-one':
                        field.selectedIndex = -1
                        break
                    default:
                        field.value = ''
                }
            }
        })
    }
}