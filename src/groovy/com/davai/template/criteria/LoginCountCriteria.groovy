package com.davai.template.criteria

import com.davai.template.*

public class LoginCountCriteria extends Criteria {

	def doFindAll() {
		return LoginCount.findAll()
	}
	
	def doFindAll(queryString, arguments) {
		log.trace "finding: " + ["queryString":queryString,"arguments":arguments]
		return LoginCount.findAll(queryString, arguments)
	}
}