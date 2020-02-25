package com.gongsi.app.persistence;

import com.gongsi.app.persistence.model.User;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserDao extends HibernateDao implements UserDao {
    @Override
    public void create(User user) {
        Objects.requireNonNull(user);
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
        Objects.requireNonNull(id);
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
        Objects.requireNonNull(user);
        updateObject(user);
    }

    @Override
    public void remove(User user) {
        Objects.requireNonNull(user);
        removeObject(user);
    }

}
