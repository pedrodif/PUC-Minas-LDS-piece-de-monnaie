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
}