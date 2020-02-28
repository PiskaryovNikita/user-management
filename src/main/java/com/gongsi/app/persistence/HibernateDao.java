package com.gongsi.app.persistence;

import java.util.List;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//TODO: refactor dao layer, generics(no unchecked), naming, check hibernate best practices
@Slf4j
@Repository
public class HibernateDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void createObject(Object object) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(object);
        } catch (Exception e) {
            log.error("unknown error", e);
            throw new RuntimeException(e);
        }
    }

    public Object findObject(String hql, Object searchValue) {
        Object obj;
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
            log.error("HibernateDao", e);
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findList(String hql) {
        List<T> objects;
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery(hql);
            if (query.getResultList().isEmpty()) {
                return null;
            }
            objects = query.getResultList();
            return objects;
        } catch (Exception e) {
            log.error("HibernateDao", e);
            throw new RuntimeException(e);
        }
    }

    public void updateObject(Object object) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.clear();
            session.update(object);
        } catch (Exception e) {
            log.error("HibernateDao", e);
            throw new RuntimeException(e);
        }
    }

    public <T> void removeObject(T object) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.clear();
            session.remove(object);
        } catch (Exception e) {
            log.error("HibernateDao", e);
            throw new RuntimeException("HibernateDao", e);
        }
    }
}
