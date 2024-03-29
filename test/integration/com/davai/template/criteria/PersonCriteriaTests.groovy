package com.davai.template.criteria

import com.davai.template.Person
import com.davai.template.criteria.PersonCriteria

class PersonCriteriaTests extends GroovyTestCase {
	def objectService
	
	void testFindByUsername() {
        
        def inputUsername = "inputUsername"
        def existingPerson = new Person(
            username: inputUsername,
            password: "inputPassword", 
            name: "inputName",
            accountLocked:"false",
            accountExpired:"false",
            enabled:"true")
            
        def otherPerson = new Person(
            username: "otherpersonUsername",
            password: "otherPassword", 
            name: "otherName",
            accountLocked:"false",
            accountExpired:"false",
            enabled:"true")    
            
        assert existingPerson.save(flush:true)
        assert otherPerson.save(flush:true)
        
        def personCriteria = new PersonCriteria(
        	queryString: "from Person p where p.username = :username ",
        	arguments:[username: existingPerson.username]
        )
        
		//EXECUTE
		def persons = objectService.find(personCriteria)
		
		//VERIFY
		assertEquals(1, persons.size())
		assertEquals(existingPerson.name, persons[0].name)
	}
}