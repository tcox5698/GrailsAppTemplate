package com.davai.template.criteria

import com.davai.template.LoginCount
import com.davai.template.Person

class LoginCountCriteriaTests extends GroovyTestCase {
	def objectService
	
	void testFindByPersonId() {
        
        def existingPerson = new Person(
            username: "inputEmailAddress",
            password: "inputPassword", 
            name: "inputName",
            accountLocked:"false",
            accountExpired:"false",
            enabled:"true")
            
        assert existingPerson.save(flush:true)
        
        def existingLoginCount = new LoginCount(
        	person: existingPerson,
        	countValue: new BigDecimal("23")
        )
        
        assert existingLoginCount.save(flush:true)
        
        def arguments = [person:existingPerson] 
        
        def countCriteria = new LoginCountCriteria(
        	queryString: " from LoginCount c where c.person = :person",
        	arguments: arguments
        )
        
		//EXECUTE
		def readLoginCounts = objectService.find(countCriteria)
		
		//VERIFY
		assertEquals(1, readLoginCounts.size())
		assertEquals(23, readLoginCounts[0].countValue.intValue())
	}
}