package com.davai.template

import com.davai.template.criteria.*
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class UserDashboardController {
	def objectService
	def springSecurityService
	def grailsApplication

    def index() { 
    	def person = springSecurityService.currentUser
//    	def arguments = [person:person]
//    	
//    	def criteria = new UnlockedAchievementCriteria(
//    			queryString: "from UnlockedAchievement a where a.person = :person order by a.unlockedDate desc",
//    			arguments:arguments,
//    			maxResults:5		
//    	)
//    	
//    	def unlockedAchievements = objectService.find(criteria)
//
//
//    	render view:"index", model: [unlockedAchievements: unlockedAchievements]	

        render view:"index"
    }
}
