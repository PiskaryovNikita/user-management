package com.gongsi.app.resource;

import com.gongsi.app.persistence.model.Role;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleResource {

    @GET
    public List<Role> getRoles() {
        return new ArrayList<>(EnumSet.allOf(Role.class));
    }

    @GET
    @Path("/{name}")
    public Role getRoleById(@PathParam("name") String name) {
        return Role.valueOf(name);
    }
}
