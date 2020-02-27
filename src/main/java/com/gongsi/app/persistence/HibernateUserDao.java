package com.gongsi.app.persistence;

import com.gongsi.app.persistence.model.User;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserDao extends HibernateDao implements UserDao {
    @Override
    public void create(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("user must be not null");
        }
        createObject(user);
    }

    @Override
    public User findByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new IllegalArgumentException("email must be not null");
        }
        String hql = "FROM User where email = :search_factor";
        return (User) findObject(hql, email);
    }

    @Override
    public User findByLogin(String login) {
        if (Objects.isNull(login)) {
            throw new IllegalArgumentException("login must be not null");
        }
        String hql = "FROM User where login = :search_factor";
        return (User) findObject(hql, login);
    }

    @Override
    public User findById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id must be not null");
        }
        String hql = "FROM User where id = :search_factor";
        return (User) findObject(hql, id);
    }

    @Override
    public List<User> findAll() {
        String hql = "FROM User";
        return findList(hql);
    }

    @Override
    public void update(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("user must be not null");
        }
        updateObject(user);
    }

    @Override
    public void remove(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("user must be not null");
        }
        removeObject(user);
    }

}
