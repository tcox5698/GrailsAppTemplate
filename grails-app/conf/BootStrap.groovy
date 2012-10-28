import com.davai.template.EventService
import com.davai.template.event.*

class BootStrap {
	def eventService

    def init = { servletContext ->
    	log.info "initing BootStrap"
    	eventService.registerHandler(LoginEvent.class, "loginEventHandlerService")
    	
    	
    }
    def destroy = {
    }
}
