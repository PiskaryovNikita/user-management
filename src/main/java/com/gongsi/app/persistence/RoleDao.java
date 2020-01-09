package com.gongsi.app.persistence;

import java.util.List;

import com.gongsi.app.model.Role;

public interface RoleDao {
	void create(Role role);
	void update(Role role);
	void remove(Role role);
	Role findById(Long roleId);
	Role findByName(String name);
	List<Role> findAll();
}
