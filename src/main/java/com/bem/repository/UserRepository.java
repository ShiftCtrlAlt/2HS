package com.bem.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.bem.domain.LocalAuth;
import com.bem.domain.User;

@Component
@Transactional
public class UserRepository extends BaseRepository<User>{
	@PersistenceContext
	private EntityManager entityManager;	//实体管理器
	
	//保存用户信息
	public void SaveUser(User user){
		try {
			getSession().save(user);
		} catch (RuntimeException e) {
			log.error("SaveUser error",e);
			throw e;
		}
	}
	
	//保存账号信息
	public void SaveLocalAuth(LocalAuth localAuth){
		getSession().save(localAuth);
		getSession().flush();
		getSession().clear();
	}
	
	//修改用户信息
	public void UpdateUser(User user){
		try {
			getSession().update(user);
		} catch (RuntimeException e) {
			log.error("UpdateUser error",e);
			throw e;
		}
	}
	public User findUserByUserId(String userId){
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> query  = builder.createQuery(User.class);
			Root<User> root =query.from(User.class);
			query.where(builder.equal(root.get("id"),(userId)));
			User user = entityManager.createQuery(query).getSingleResult();
			log.debug("findUserByUserId successfull");
			return user;
		} catch (RuntimeException e) {
			log.error("findUserByUserId error",e);
			throw e;
		}
		
	}
	public LocalAuth findUserByAccount(String account){
		LocalAuth localAuth = new LocalAuth();
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<LocalAuth> query  = builder.createQuery(LocalAuth.class);
			Root<LocalAuth> root =query.from(LocalAuth.class);
			Predicate conditionForName = builder.equal(root.get("username"), account);
		    Predicate conditionForEmail = builder.equal(root.get("email"), account);
		    Predicate conditionForPhone = builder.equal(root.get("phone"), account);
		    Predicate condition3 = builder.or(conditionForName,conditionForEmail,conditionForPhone);
			query.where(condition3);
			localAuth = entityManager.createQuery(query).getSingleResult();
			log.debug("findUserByAccount successfull");
			return localAuth;
		}catch(Exception e){
			log.error("findUserByAccount error",e);
			return null;
		}
	}	
}
