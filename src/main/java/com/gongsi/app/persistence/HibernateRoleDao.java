package com.gongsi.app.persistence;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.gongsi.app.model.Role;

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

	public void removeAll() {
		removeAllObjects("DELETE Role");
	}

	@Override
	public Role findById(Long roleId) {
		Objects.requireNonNull(roleId);
		String hql = "FROM Role R WHERE R.roleId = :search_factor";
		return (Role) findObject(hql, roleId);
	}

	@Override
	public Role findByName(String name) {
		Objects.requireNonNull(name);
		String hql = "FROM Role R WHERE R.name = :search_factor";
		Role result = (Role) findObject(hql, name);
		return result;
	}

	public List<Role> findAll() {
		String hql = "FROM Role";
		return findList(hql);
	}
}
