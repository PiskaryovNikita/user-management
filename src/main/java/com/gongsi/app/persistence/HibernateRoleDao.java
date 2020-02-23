package com.gongsi.app.persistence;

import com.gongsi.app.persistence.model.Role;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class HibernateRoleDao extends HibernateDao implements RoleDao {

    @Override
    public void create(Role role) {
        Objects.requireNonNull(role);
        createObject(role);
    }

    @Override
    public void update(Role role) {
        Objects.requireNonNull(role);
        updateObject(role);
    }

    @Override
    public void remove(Role role) {
        Objects.requireNonNull(role);
        removeObject(role);
    }

    @Override
    public Role findById(Long roleId) {
        Objects.requireNonNull(roleId);
        String hql = "FROM Role WHERE id = :search_factor";
        return (Role) findObject(hql, roleId);
    }

    @Override
    public Role findByName(String name) {
        Objects.requireNonNull(name);
        String hql = "FROM Role WHERE name = :search_factor";
        Role result = (Role) findObject(hql, name);
        return result;
    }

    public List<Role> findAll() {
        String hql = "FROM Role";
        return findList(hql);
    }
}
