package com.davai.template

import com.davai.template.event.*
import com.davai.template.handler.*
import org.springframework.web.context.support.WebApplicationContextUtils 
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.springframework.context.ApplicationContext 
import org.springframework.context.support.GenericApplicationContext

class EventService {

	def handlerRegistry = [:]

    def processEvent(Event event) {
		def handlerName = handlerRegistry[event.getClass().name]
		
		GenericApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(ServletContextHolder.getServletContext())
			
	    def EventHandler handler = ctx.getBean(handlerName)    
		handler.handleEvent(event)
    }
    
    def registerHandler(Class eventClass, String handlerName) {    
    	handlerRegistry[eventClass.name] = handlerName;
    }
}
