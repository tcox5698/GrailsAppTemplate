package com.davai.template

import groovy.text.SimpleTemplateEngine
import com.davai.template.*
import com.davai.secure.*

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH
import org.codehaus.groovy.grails.plugins.springsecurity.NullSaltSource
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.ui.RegistrationCode

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class RegisterController extends com.davai.template.AbstractS2UiController {

    static defaultAction = 'index'

    def mailService
    def saltSource

    def index = {
        [command: new RegisterCommand()]
    }

    def register = { RegisterCommand command ->
        if (command.hasErrors()) {
            render view: 'index', model: [command: command]
            return
        }

        def user = Person.newInstance(name: command.personName, username: command.email,
            password: command.password, accountLocked: true, enabled: true)
        if (!user.validate() || !user.save()) {
            throw new RuntimeException("what happened to user: ${user} he got errors: ${user.errors}")
        }

        def registrationCode = new RegistrationCode(username: user.username).save()
        String url = generateLink('verifyRegistration', [t: registrationCode.token])

        def conf = SpringSecurityUtils.securityConfig
        def body = conf.ui.register.emailBody
        if (body.contains('$')) {
            body = evaluate(body, [user: user, url: url])
        }
        mailService.sendMail {
            to command.email
            from conf.ui.register.emailFrom
            subject conf.ui.register.emailSubject
            html body.toString()
        }

        render view: 'index', model: [emailSent: true]
    }

    def verifyRegistration = {
        def conf = SpringSecurityUtils.securityConfig
        String defaultTargetUrl = conf.successHandler.defaultTargetUrl

        String token = params.t
        
        def registrationCode = token ? RegistrationCode.findByToken(token) : null
        
        
        if (!registrationCode) {   
            flash.error = message(code: 'spring.security.ui.register.badCode')
            redirect uri: defaultTargetUrl
            return
        }

        def user
        RegistrationCode.withTransaction { status ->
            user = Person.findByUsername(registrationCode.username)
            if (!user) {
                return
            }
            user.accountLocked = false
            user.save()
            for (roleName in conf.ui.register.defaultRoleNames) {
                SecUserSecRole.create user, SecRole.findByAuthority(roleName)
            }
            registrationCode.delete()
        }

        if (!user) {
            flash.error = message(code: 'spring.security.ui.register.badCode')
            redirect uri: defaultTargetUrl
            return
        }

        springSecurityService.reauthenticate user.username

        flash.message = message(code: 'spring.security.ui.register.complete')              
        
        redirect uri: conf.ui.register.postRegisterUrl ?: defaultTargetUrl
    }

    def forgotPassword = {
        if (!request.post) {
            // show the form
            return
        }

        String username = params.username
        if (!username) {
            flash.error = message(code: 'spring.security.ui.forgotPassword.username.missing')
            return
        }

        def user = SecUser.findByUsername(username)
        if (!user) {
            flash.error = message(code: 'spring.security.ui.forgotPassword.user.notFound')
            return
        }

        def registrationCode = new RegistrationCode(username: user.username).save()

        String url = generateLink('resetPassword', [t: registrationCode.token])

        def conf = SpringSecurityUtils.securityConfig
        def body = conf.ui.forgotPassword.emailBody
        if (body.contains('$')) {
            body = evaluate(body, [user: user, url: url])
        }
			
        mailService.sendMail {
            to user.email
            from conf.ui.forgotPassword.emailFrom
            subject conf.ui.forgotPassword.emailSubject
            html body.toString()
        }		

        [emailSent: true]
    }

    def resetPassword = { ResetPasswordCommand command ->

        String token = params.t

        def registrationCode = token ? RegistrationCode.findByToken(token) : null
        if (!registrationCode) {
            flash.error = message(code: 'spring.security.ui.resetPassword.badCode')
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
            return
        }

        if (!request.post) {
            return [token: token, command: new ResetPasswordCommand()]
        }

        command.username = registrationCode.username
        command.validate()

        if (command.hasErrors()) {
            return [token: token, command: command]
        }

        String salt = saltSource instanceof NullSaltSource ? null : registrationCode.username
        RegistrationCode.withTransaction { status ->
            def user = SecUser.findByUsername(registrationCode.username)
            user.password = command.password
            user.save()
            registrationCode.delete()
        }

        springSecurityService.reauthenticate registrationCode.username

        flash.message = message(code: 'spring.security.ui.resetPassword.success')

        def conf = SpringSecurityUtils.securityConfig
        String postResetUrl = conf.ui.register.postResetUrl ?: conf.successHandler.defaultTargetUrl
        redirect uri: postResetUrl
    }

    protected String generateLink(String action, linkParams) {
        createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
            controller: 'register', action: action,
            params: linkParams)

    }

    protected String evaluate(s, binding) {
        new SimpleTemplateEngine().createTemplate(s).make(binding)
    }

    static final passwordValidator = { String password, command ->
        if (command.email && command.email.equals(password)) {
            return 'command.password.error.username'
        }

        if (password && password.length() >= 8 && password.length() <= 64 &&
            (!password.matches('^.*\\p{Alpha}.*$') ||
                !password.matches('^.*\\p{Digit}.*$') ||
                !password.matches('^.*[!@#$%^&].*$'))) {
            return 'command.password.error.strength'
        }
    }

    static final password2Validator = { value, command ->
        if (command.password != command.password2) {
            return 'command.password2.error.mismatch'
        }
    }
}

class RegisterCommand {

    String personName    
    String email
    String password
    String password2

    static constraints = {
        personName blank: false        
        email blank: false, email: true, validator: { value, command ->
            if (value) {
                if (Person.findByUsername(value)) {
                    return 'registerCommand.username.unique'
                }
            }
        }
        password blank: false, minSize: 8, maxSize: 64, validator: RegisterController.passwordValidator
        password2 validator: RegisterController.password2Validator
    }
}

class ResetPasswordCommand {
    String username
    String password
    String password2

    static constraints = {
        password blank: false, minSize: 8, maxSize: 64, validator: RegisterController.passwordValidator
        password2 validator: RegisterController.password2Validator
    }
}