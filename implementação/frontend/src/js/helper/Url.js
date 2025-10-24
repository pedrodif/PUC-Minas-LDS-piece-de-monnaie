export class Url {
    static getParams() {
        const urlParams = new URLSearchParams(window.location.search)
        return {}
    }

    static addQueryParam(param, value) {
        const currentUrl = new URL(window.location.href)
        currentUrl.searchParams.set(param, value)
        window.history.pushState({}, '', currentUrl)
    }

    static removeQueryParam(param) {
        const urlParams = new URLSearchParams(window.location.search)
        urlParams.delete(param)

        const newQueryString = urlParams.toString()
        const newUrl = newQueryString
            ? `${window.location.pathname}?${newQueryString}`
            : window.location.pathname

        window.history.replaceState({}, '', newUrl)
    }

    static reload(param, value) {
        const url = new URL(window.location.href)

        if (param && value)
            url.searchParams.set(param, value)

        window.location.href = url.toString()
    }

    static mountQueryString(url, queryParams) {
        url.search = new URLSearchParams(queryParams).toString()
    }

    static mountFragment(url, fragment) {
        url.hash = fragment
    }

    static mountRoutes({ queryParams = {}, hash = '' } = {}) {
        const currentUrl = new URL(window.location.href)
        let routes = {}

        for (let endpoint in Url.ENDPOINT_FILES) {
            const params = queryParams[endpoint] || {}
            const fragment = hash[endpoint] || ''

            let url = new URL(Url.ENDPOINT_FILES[endpoint], currentUrl.origin)

            Url.mountQueryString(url, params)
            Url.mountFragment(url, fragment)

            routes[endpoint] = url.href
        }

        return routes
    }

    static ENDPOINT_FILES = {
        login: '/',
    }
}