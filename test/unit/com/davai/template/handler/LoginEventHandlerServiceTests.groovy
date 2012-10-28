package com.davai.template.handler

import com.davai.template.event.*
import com.davai.template.*
import com.davai.template.criteria.*

import groovy.mock.interceptor.*
import grails.test.mixin.*

@TestFor(LoginEventHandlerService)
@Mock(Person)
class LoginEventHandlerServiceTests  {
    def inputUsername = "inputUsername"
    def loginEvent = new LoginEvent(username:inputUsername)	
    def objectServiceController 
    def existingCountValue = 6
    def expectedPerson
    
    @Before
    void setUp() {
        objectServiceController = mockFor(ObjectService)       
        def loginEvent = new LoginEvent(username:inputUsername)	
        expectedPerson = new Person(
            username: inputUsername,
            password: "expectedPassword", 
            name: "expectedName",
            accountLocked:"false",
            accountExpired:"false",
            enabled:"true"
        )            
    }    
    
    

    //	def testHandleEvent_AwardsAchievementOnFirstLogin() {
    //	    mockDomain(Person,[expectedPerson])
    //	
    //		objectServiceController.demand.find(1) { personCriteria ->
    //			assertEquals(inputUsername, personCriteria.arguments.username)
    //			return [expectedPerson]
    //		}
    //					 
    //		objectServiceController.demand.find(1) { countCriteria ->
    //			assertEquals(expectedPerson, countCriteria.arguments.person)
    //			return []
    //		}
    //				
    //		objectServiceController.demand.save(1) { loginCount ->
    //			assertEquals(1, loginCount.countValue)	
    //			assertEquals(expectedPerson, loginCount.person)			
    //		}
    //		
    //		objectServiceController.demand.save(1) { unlockedAchievement ->
    //			assertEquals(expectedPerson, unlockedAchievement.person)
    //			assertEquals("achievement.msg.login.singular", unlockedAchievement.messageKey)
    //			assertEquals("1", unlockedAchievement.messageArguments)
    //		}
    //		
    //		objectServiceController.use {
    //			handler.objectService = new ObjectService()
    //		
    //			//EXECUTE
    //			handler.handleEvent(loginEvent)
    //		}	
    //	}
	
    void testHandleEvent_UpdatesLoginStatistic_IfNonFibonacci_ThenNoAchievement() {
        objectServiceController.demand.find(1) { PersonCriteria personCriteria ->
            assertEquals(inputUsername, personCriteria.arguments.username)
            return [expectedPerson]
        }	
	
        def expectedLoginCount = new LoginCount(person: expectedPerson,
            countValue: existingCountValue)
	
        objectServiceController.demand.find(1) { LoginCountCriteria countCriteria ->
            assertEquals(expectedPerson, countCriteria.arguments.person)
            return [expectedLoginCount]
        }
		
        objectServiceController.demand.save(1) { loginCount ->
            assertEquals(existingCountValue + 1, loginCount.countValue)	
            assertEquals(expectedLoginCount.person, loginCount.person)		
        }
        

        service.objectService = objectServiceController.createMock()        
		
        //EXECUTE
        service.handleEvent(loginEvent)
        	
    }
	
    void testIsFibonacci() {
        //EXECUTE
        assertTrue("zero should be fib",service.isFibonacci(0)) 
        assertTrue("1 should be fib",service.isFibonacci(1)) 
        assertTrue("2 should be fib",service.isFibonacci(2)) 
        assertTrue("3 should be fib",service.isFibonacci(3)) 
        assertFalse("4 should NOT be fib",service.isFibonacci(4)) 
        assertTrue("5 should be fib",service.isFibonacci(5)) 						
        assertFalse("6 should NOT be fib",service.isFibonacci(6)) 						
        assertFalse("7 should NOT be fib",service.isFibonacci(7)) 						
        assertTrue("8 should be fib",service.isFibonacci(8)) 						
        assertFalse("9 should NOT be fib",service.isFibonacci(9)) 						
        assertFalse("10 should NOT be fib",service.isFibonacci(10)) 														
        assertFalse("11 should NOT be fib",service.isFibonacci(11)) 			
        assertFalse("12 should NOT be fib",service.isFibonacci(12)) 			
        assertTrue("13 should be fib",service.isFibonacci(13)) 			
        assertFalse("14 should NOT be fib",service.isFibonacci(14)) 			
        assertFalse("15 should NOT be fib",service.isFibonacci(15)) 													
        assertFalse("16 should NOT be fib",service.isFibonacci(16)) 		
    }
}