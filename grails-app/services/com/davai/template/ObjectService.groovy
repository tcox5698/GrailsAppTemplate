package com.davai.template

import com.davai.template.criteria.*

class ObjectService {
	def sessionFactory

	public Object read(Class clazz, Object id) {
		return sessionFactory.getCurrentSession().load(clazz, id)
	}
	
	def find(Criteria criteria) {
		return criteria.find()
	}
	
	public void save(Object object) {
		object.save()
	}
}