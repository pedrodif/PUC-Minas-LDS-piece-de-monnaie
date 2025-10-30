
import { Form } from '../../component/Form.js'
import { Session } from '../../../middleware/Session.js'
import { Service } from '../../../application/Service.js'

Session.watch()
const loginService = new Service('/auth')
const form = new Form(document.querySelector('form'))

const handleSubmit = (data) =>  {
    const response = loginService.create(data)
    
    if (response !== false) {
        Session.create(response)
        window.location.href = ''
    }
}

form.onSubmit(handleSubmit)