export class Utilities {
    static base64ToFile(base64, filename, contentType = 'image/png') {
        const base64Data = base64.split(',')[1] || base64
        const byteCharacters = atob(base64Data)
        const byteNumbers = new Array(byteCharacters.length)

        for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i)
        }

        const byteArray = new Uint8Array(byteNumbers)
        return new File([byteArray], filename, { type: contentType })
    }

    static formatDateTime(value) {
        const dateOptions = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        }
        return new Date(value).toLocaleString('pt-BR', dateOptions)
    }
}