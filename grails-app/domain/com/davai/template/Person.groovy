package com.davai.template

import com.davai.secure.*

class Person extends SecUser {

    String name
    
    String email

    static constraints = {
        name(unique:true)
        email(display:false, nullable: true)
    }
    
	def beforeInsert() {
	    super.beforeInsert()
		email = username;
	}

	def beforeUpdate() {
	    super.beforeUpdate()
		email = username;
	}    
    
	public String toString() {
		return "Person:{id:$id,name:$name}"
	} 
    
	public void setPassword(String inputPassword) {
	    if (null != inputPassword && inputPassword.trim().length() > 0 
	        && !inputPassword.equals(getPassword())) {
                super.setPassword(inputPassword)
	    }
	}    
}
