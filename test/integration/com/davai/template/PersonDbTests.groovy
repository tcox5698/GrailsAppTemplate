package com.davai.template

import static org.junit.Assert.*
import org.junit.*
import com.davai.secure.SecUser

class PersonDbTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSomething() {
        def inputEmailAddress = "inputEmailAddress"
		def existingPerson = new Person(
			username: inputEmailAddress,
			password: "inputPassword", 
			name: "inputName",
			accountLocked:"false",
			accountExpired:"false",
			enabled:"true")
            
        existingPerson.save(flush:true)
        
        assert !existingPerson.errors.hasErrors()
        assert existingPerson.id
        
        assertEquals "inputName", existingPerson.name  
               
        def p = Person.read(existingPerson.id)
                        
        p = SecUser.findByUsername(inputEmailAddress)
        
        assertEquals "inputName", p.name
    }
}
