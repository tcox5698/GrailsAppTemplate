package com.davai.template.listener

import org.springframework.context.ApplicationListener 
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import com.davai.template.event.*
import org.springframework.web.context.support.WebApplicationContextUtils 
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.springframework.context.ApplicationContext 

class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
	def eventService

	void onApplicationEvent(AuthenticationSuccessEvent event) { 
		def ctx = WebApplicationContextUtils.getWebApplicationContext(ServletContextHolder.getServletContext()); 
		
		eventService = ctx.getBean("eventService")
	
		def loginEvent = new LoginEvent(username: event.source.principal.username)
		eventService.processEvent(loginEvent) 
	} 
}