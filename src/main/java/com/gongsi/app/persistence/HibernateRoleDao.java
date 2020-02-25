package com.gongsi.app.persistence;

import com.gongsi.app.persistence.model.Role;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateRoleDao extends HibernateDao implements RoleDao {

    @Override
    public void create(Role role) {
        createObject(role);
    }

    @Override
    public void update(Role role) {
        updateObject(role);
    }

    @Override
    public void remove(Role role) {
        removeObject(role);
    }

    @Override
    public Role findById(Long roleId) {
        String hql = "FROM Role WHERE id = :search_factor";
        return (Role) findObject(hql, roleId);
    }

    @Override
    public Role findByName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("role name must be not null");
        }
        String hql = "FROM Role WHERE name = :search_factor";
        return (Role) findObject(hql, name);
    }

    public List<Role> findAll() {
        String hql = "FROM Role";
        return findList(hql);
    }
}
