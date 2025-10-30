import { Loader } from '../presentation/component/Loader.js'

export class RequestHelper {
    static async execute(callback) {
        Loader.getLoader().show()

        try {
            const response = await callback()
            await new Promise(resolve => setTimeout(resolve, 1200))
            return response
        } finally {
            Loader.getLoader().hide()
        }
    }
}