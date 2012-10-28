package com.davai.template

class LoginCount {
	Integer countValue
	Person person
	
	public String toString() {
        return id + ":[" + this.person + "]: " + countValue
    } 
}