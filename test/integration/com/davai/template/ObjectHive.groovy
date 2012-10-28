package com.davai.template

def class ObjectHive {
	def incrementer = 0

	def validateIt(obj) {
		if (!obj.validate()) {
			obj.errors.allErrors.each {
				println "\n" + it 
			}	
			throw new RuntimeException("test object failed validation" + obj.errors.allErrors)		
		}
	}

	
	

	def providePeople(count = 1) {
		def counter = 0
		def people = []
		
		while (counter++ < count) {
			incrementer++
			def existingPerson = new Person(
				username: "inputUsername" + incrementer,
				password: "inputPassword" + incrementer, 
				name: "inputName" + incrementer,
				accountLocked:"false",
				accountExpired:"false",
				enabled:"true")
			validateIt(existingPerson)
			assert existingPerson.save(flush:true)	
			people.add(existingPerson)
		}
		
		return people
	}	
}