package com.gongsi.rest.resource;

import com.gongsi.app.persistence.model.User;
import com.gongsi.app.service.UserService;
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

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserService userService;

    @POST
    public Response addUser(User user, @Context UriInfo uriInfo) {
        userService.create(user);
        User newUser = userService.findByLogin(user.getLogin());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newUser.getId() + "").build();
        // builder pattern
        return Response.created(uri)// 201 + Location header
                .entity(newUser).build();
    }

    @GET
    public List<User> getUsers(@BeanParam UserFilterBean filterBean, @Context UriInfo uriInfo) {
        if (filterBean.getYear() != null) {
            return userService.filterByYear(filterBean.getYear());
        }
        if (filterBean.getStart() != null && filterBean.getSize() != null) {
            return userService.paginatedUsers(filterBean.getStart(), filterBean.getSize());
        }
        if (filterBean.getRoleId() != null) {
            return userService.filterByRole(filterBean.getRoleId());
        }
        return userService.findAll();
    }

    @GET
    @Path("/{userId}")
    public User getUserById(@PathParam("userId") Long userId, @Context UriInfo uriInfo) {
        return userService.findById(userId);
    }

    @PUT
    @Path("/{userId}")
    // conversion from json to java obj
    public User updateUser(@PathParam("userId") Long userId, User user, @Context UriInfo uriInfo) {
        user.setId(userId);
        userService.update(user);
        return user;
    }

    @DELETE
    @Path("/{userId}")
    public void deleteUser(@PathParam("userId") Long userId) {
        userService.remove(new User(userId));
    }
}
