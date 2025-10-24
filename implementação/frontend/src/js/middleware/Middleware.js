export class Middleware {
    static create() {
        throw new Error('Método create deve ser implementado nas classes filhas')
    }
}