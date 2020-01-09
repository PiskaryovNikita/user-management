package com.gongsi.soap;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.gongsi.app.model.Role;
import com.gongsi.app.service.RoleService;

@WebService(endpointInterface = "com.nixsolutions.wsSoap.RoleSoap", serviceName = "RoleService", targetNamespace = "com.nixsolutions.wsSoap.role")
public class RoleSoapImpl extends SpringBeanAutowiringSupport implements RoleSoap {
	@Autowired
	private RoleService roleService;

	@Override
	public void createRole(Role role) {
		roleService.create(role);
	}

	@Override
	public void updateRole(Role role) {
		roleService.update(role);
	}

	@Override
	public void removeRole(Role role) {
		roleService.remove(role);
	}

	@Override
	public Role findRoleById(Long roleId) {
		return roleService.findById(roleId);
	}

	@Override
	public Role findRoleByName(String name) {
		return roleService.findByName(name);
	}

	@Override
	public List<Role> findAllRoles() {
		return roleService.findAll();
	}
}
