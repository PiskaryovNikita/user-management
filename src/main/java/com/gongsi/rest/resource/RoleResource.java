package com.gongsi.rest.resource;

import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.service.RoleService;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleResource {
    private final RoleService roleService;

    @POST
    public Response addRole(@Context UriInfo uriInfo, Role role) {
        roleService.create(role);
        URI uri = uriInfo.getAbsolutePathBuilder().path(role.getId() + "").build();
        return Response.created(uri).entity(role).build();
    }

    @GET
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @GET
    @Path("/{roleId}")
    public Role getRoleById(@PathParam("roleId") Long roleId) {
        return roleService.findById(roleId);
    }

    @PUT
    @Path("/{roleId}")
    public Role updateRole(@PathParam("roleId") Long roleId, Role role, @Context UriInfo uriInfo) {
        role.setId(roleId);
        roleService.update(role);
        return role;
    }

    @DELETE
    @Path("/{roleId}")
    public void deleteRole(@PathParam("roleId") Long roleId) {
        roleService.remove(new Role(roleId));
    }
}
