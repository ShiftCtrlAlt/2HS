package com.bem.domain;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Component;



import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
@Transactional
public class TaskRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	public void save(Task task) {
		getSession().save(task);
	}
	public List<Task> findAll(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Task> query = builder.createQuery(Task.class);
		Root<Task> root  = query.from(Task.class);
		query.select(root);
		List<Task> mlist = entityManager.createQuery(query).getResultList();
		return mlist;
	}
}
