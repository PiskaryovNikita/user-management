package com.gongsi.app.persistence;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class HibernateDao {
	@Autowired
	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(HibernateDao.class);


	public void createObject(Object object) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(object);
		} catch (Exception e) {
			logger.error("HibernateDao", e);
			throw new RuntimeException(e);
		}
	}

	public Object findObject(String hql, Object searchValue) {
		Object obj = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("search_factor", searchValue);
			if (query.getResultList().isEmpty()) {
				return null;
			}
			obj = query.getSingleResult();
			return obj;
		} catch (Exception e) {
			logger.error("HibernateDao", e);
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findList(String hql) {
		List<T> objects = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(hql);
			if (query.getResultList().isEmpty()) {
				return null;
			}
			objects = query.getResultList();
			return objects;
		} catch (Exception e) {
			logger.error("HibernateDao", e);
			throw new RuntimeException(e);
		}
	}

	public void updateObject(Object object) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.clear();
			session.update(object);
		} catch (Exception e) {
			logger.error("HibernateDao", e);
			throw new RuntimeException(e);
		}
	}

	public <T> void removeObject(T object) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.clear();
			session.remove(object);
		} catch (Exception e) {
			logger.error("HibernateDao", e);
			throw new RuntimeException("HibernateDao", e);
		}
	}

	public void removeObject(String hql, Long delValue) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("del_factor", delValue);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("HibernateDao", e);
			throw new RuntimeException(e);
		}
	}

	public void removeAllObjects(String hql) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(hql);
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("HibernateDao", e);
			throw new RuntimeException(e);
		}
	}

	public void setSessionFactory() {
		new Configuration().configure().buildSessionFactory();
	}
}
