package com.gongsi.rest.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.gongsi.app.model.Role;
import com.gongsi.app.service.RoleService;

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleResource {
	@Autowired
	private RoleService roleService;

	@POST
	public Response addRole(@Context UriInfo uriInfo, Role role) {
		roleService.create(role);
		URI uri = uriInfo.getAbsolutePathBuilder().path(role.getRoleId() + "").build();
		return Response.created(uri).entity(role).build();
	}

	@GET
	public List<Role> getRoles(@Context UriInfo uriInfo) {
		List<Role> roles = roleService.findAll();
		return getRolesWithLinks(roles, uriInfo);
	}

	@GET
	@Path("/{roleId}")
	public Role getRoleById(@PathParam("roleId") Long roleId, @Context UriInfo uriInfo) {
		Role role = roleService.findById(roleId);
		return setRoleLinkAndReturn(uriInfo, role);
	}

	@PUT
	@Path("/{roleId}")
	public Role updateRole(@PathParam("roleId") Long roleId, Role role, @Context UriInfo uriInfo) {
		role.setRoleId(roleId);
		roleService.update(role);
		return setRoleLinkAndReturn(uriInfo, role);
	}

	@DELETE
	@Path("/{roleId}")
	public void deleteRole(@PathParam("roleId") Long roleId) {
		roleService.remove(new Role(roleId));
	}

	private Role setRoleLinkAndReturn(UriInfo uriInfo, Role role) {
		String roleUrl = uriInfo.getBaseUriBuilder().path(RoleResource.class).path(role.getRoleId() + "").build()
				.toString();
		Link linkRole = Link.fromUri(roleUrl).rel("role").build();
		role.setLink(linkRole);
		return role;
	}

	private List<Role> getRolesWithLinks(List<Role> roles, UriInfo uriInfo) {
		for (Role role : roles) {
			setRoleLinkAndReturn(uriInfo, role);
		}
		return roles;
	}
}
