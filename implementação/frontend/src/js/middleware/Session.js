import { Middleware } from './Middleware.js'
import { Url } from '../helper/Url.js'

export class Session extends Middleware {
    static TOKEN_MAX_AGE = 24 * 60 * 60 * 1000

    static create(user) {
        sessionStorage.setItem('session', JSON.stringify({
            shouldNotify: true,
            createdAt: Date.now()
        }))
        
        const { token } = user

        sessionStorage.setItem('token', JSON.stringify({
            value: token,
            createdAt: Date.now()
        }))

        delete user.token
        sessionStorage.setItem('user', JSON.stringify(user))
    }

    static kill() {
        const { userID } = Session.userProvider()

        localStorage.setItem('logout', JSON.stringify({
            userID,
            app: 'piece-de-monnaie',
            timestamp: Date.now(),
            sessionDuration: Session.duration()
        }))

        sessionStorage.clear()
    }

    static watch() {
        if (!window._listenerLogout) {
            window.addEventListener('storage', (event) => {
                if (event.key === 'logout' && event.newValue) {

                    const loggedoutUser = JSON.parse(event.newValue)
                    const user = Session.userProvider()

                    if (loggedoutUser.userID === user.userID) {
                        window.location.href  = Url.mountRoutes().login
                        sessionStorage.clear()
                    }
                }
            })

            window._listenerLogout = true
        }
    }

    static shouldNotify() {
        let session = null

        try {
            session = JSON.parse(sessionStorage.getItem('session')) || {}
        } catch {
            session = {}
        }

        if (session?.shouldNotify) {
            sessionStorage.setItem('session', JSON.stringify({
                ...session,
                shouldNotify: false,
            }))

            return true
        }

        return false
    }

    static duration() {
        let session = null

        try {
            session = JSON.parse(sessionStorage.getItem('session')) || {}
        } catch {
            session = {}
        }

        const sessionStart = Number(session?.createdAt)
        if (!sessionStart) {
            return { hours: 0, minutes: 0, seconds: 0 }
        }

        const diffMs = Date.now() - sessionStart
        const hours = Math.floor(diffMs / (1000 * 60 * 60))
        const minutes = Math.floor((diffMs % (1000 * 60 * 60)) / (1000 * 60))
        const seconds = Math.floor((diffMs % (1000 * 60)) / 1000)

        return { hours, minutes, seconds }
    }

    static userProvider() {
        let user = null

        try {
            user = JSON.parse(sessionStorage.getItem('user')) || {}
        } catch {
            user = {}
        }

        return user
    }

    static tokenProvider() {
        let token = null

        try {
            token = JSON.parse(sessionStorage.getItem('token')) || {}
        } catch {
            token = {}
        }

        return {
            ...token,
            ...(token.value ? { isValid: Session.isTokenValid(token) } : {})
        }
    }

    static isTokenValid(token) {
        return (Date.now() - token.createdAt) < Session.TOKEN_MAX_AGE
    }

    static updateToken(token) {
        let session = null

        try {
            session = JSON.parse(sessionStorage.getItem('session')) || {}
        } catch {
            session = {}
        }

        sessionStorage.setItem('session', JSON.stringify({
            ...session,
            shouldNotify: true,
        }))

        sessionStorage.setItem('token', JSON.stringify({
            value: token,
            createdAt: Date.now()
        }))
    }

    static updateUser(user) {
        sessionStorage.removeItem('user')
        sessionStorage.setItem('user', JSON.stringify(user))
    }
}