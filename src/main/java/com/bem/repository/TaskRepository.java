package com.bem.repository;


import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.bem.domain.Task;
import com.bem.utils.MyPage;

@Component
@Transactional
public class TaskRepository extends BaseRepository<Task> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 查找所有Task
	 * @return  MyPage<Task>
	 */
	public List<Task> findAll(){
		log.debug("find All Task");
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Task> query = builder.createQuery(Task.class);
			Root<Task> root  = query.from(Task.class);
			query.select(root);
			List<Task> mlist = entityManager.createQuery(query).getResultList();
			log.debug("find All Task successfull");
			return mlist;
		} catch (RuntimeException e) {
			log.error("find All Task error",e);
			throw e;
		}
		
	}
	
	/**
	 * 查找所有Task，排序（）release_time降序）
	 * @return  MyPage<Task>
	 */
	public MyPage<Task> findByPageTask(){
		log.debug("find All Task desc release_time");
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
			dc.addOrder(Order.desc("release_time"));
			log.debug("find All Task desc release_time successfull");
			return findPageByCriteria(dc);
		} catch (RuntimeException e) {
			log.error("find All Task desc release_time error",e);
			throw e;
		}
		
	}
	
	/**
	 * 通过userID查找Task
	 * @param userId
	 * @return MyPage<Task>
	 */
	public MyPage<Task> findByPageId(String userId){
		log.debug("find  MyPage<Task> By userID");
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
			dc.add(Property.forName("user_id").eq(userId));
			log.debug("find  MyPage<Task> By userID successfull");
			return findPageByCriteria(dc);
		} catch (RuntimeException e) {
			log.error("find  MyPage<Task> By userID error",e);
			throw e;
		}
	}
	/**
	 * 通过userID查找Task
	 * @param userId
	 * @return MyPage<Task>
	 */
	public MyPage<Task> findByPageId(String userId,int page){
		log.debug("find  MyPage<Task> By userID");
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
			dc.add(Property.forName("user_id").eq(userId));
			log.debug("find  MyPage<Task> By userID successfull");
			return findPageByCriteria(dc,page);
		} catch (RuntimeException e) {
			log.error("find  MyPage<Task> By userID error",e);
			throw e;
		}
	}
	
	
	/**
	 * 通过classifyID查找Task
	 * @param classify
	 * @return	MyPage<Task>
	 */
	public MyPage<Task> findByPageClassify(String classify){
		log.debug("find  MyPage<Task> By classifyID");
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
			dc.add(Restrictions.eq("classify.id", classify));
			dc.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			log.debug("find  MyPage<Task> By classifyID successfull");
			return findPageByCriteria(dc);
		} catch (RuntimeException e) {
			log.error("find  MyPage<Task> By classifyID error",e);
			throw e;
		}
	}
	
	
	/**
	 * 查一个分类下的内容
	 * @param classifyId
	 * @return
	 */
	public MyPage<Task> findOneClassify(String classifyId) {
		log.debug("find Task By classifyID");
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(Task.class);
			dc.add(Restrictions.eq("classify.id", classifyId));
			log.debug("find Task By classifyID successfull");
			return findPageByCriteria(dc);
		} catch (Exception e) {
			log.error("find Task By classifyID error",e);
			throw e;
		}
		
	}
	
	/**
	 * 通过TaskID查找Task
	 * @return  Task
	 */
	public Task findId(String id){
		log.debug("find Task By TaskID");
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Task> query = builder.createQuery(Task.class);
			Root<Task> root  = query.from(Task.class);
			query.select(root);
			query.where(builder.equal(root.get("id"), id));
			Task task = entityManager.createQuery(query).getSingleResult();
			log.debug("find Task By TaskID successfull");
			return task;
		} catch (RuntimeException e) {
			log.error("find Task By TaskID error",e);
			throw e;
		}
		
	}
	
	public void save(Task task) {
		log.debug("save Task");
		try {
			getSession().save(task);
		} catch (RuntimeException e) {
			log.error("save Task error",e);
			throw e;
		}
	}
	/**
	 * 删除一个Task，通过TaskID。（结果返回一个boolean值）
	 * @param id
	 * @return
	 */
	public boolean delete(String id){
		Task task = findId(id);
		if(task.getId()!=null){
			getSession().delete(task);
			return true;
		}
		return false;
	}
	
	
	public void update(Task task){
		log.debug("update Task");
		try {
			getSession().update(task);
		} catch (Exception e) {
			log.error("update Task error",e);;
			throw e;
		}
	}
	
}
