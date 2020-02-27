package com.gongsi.app.resource;

import com.gongsi.app.persistence.model.User;
import com.gongsi.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import javax.ws.rs.BeanParam;
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


@Api("/users")
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserService userService;

    @ApiOperation("add")
    @POST
    public Response add(User user, @Context UriInfo uriInfo) {
        userService.create(user);
        User newUser = userService.findByLogin(user.getLogin());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newUser.getId() + "").build();
        // 201 + Location header
        return Response.created(uri)
                .entity(newUser).build();
    }

    @ApiOperation("getAll")
    @GET
    public List<User> getAll(@BeanParam UserFilterBean filterBean) {
        if (filterBean.getYear() != null) {
            return userService.filterByYear(filterBean.getYear());
        }
        if (filterBean.getStart() != null && filterBean.getSize() != null) {
            return userService.paginatedUsers(filterBean.getStart(), filterBean.getSize());
        }
        if (filterBean.getRole() != null) {
            return userService.filterByRole(filterBean.getRole());
        }
        return userService.findAll();
    }

    @ApiOperation("get")
    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @ApiOperation("update")
    @PUT
    @Path("/{id}")
    // conversion from json to java obj
    public User update(@PathParam("id") Long id, User user) {
        user.setId(id);
        userService.update(user);
        return user;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        userService.remove(new User(id));
    }
}
