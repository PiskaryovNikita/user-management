package com.gongsi.rest.resource;

import java.net.URI;
import java.sql.SQLException;
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
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.gongsi.app.model.User;
import com.gongsi.app.service.UserService;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//representation layer
public class UserResource {
	@Autowired
	private UserService userService;

	@POST
	public Response addUser(User user, @Context UriInfo uriInfo) {
		userService.create(user);
		User newUser = userService.findByLogin(user.getLogin());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newUser.getUserId() + "").build();
		// builder pattern
		return Response.created(uri)// 201 + Location header
				.entity(newUser).build();
	}

	@GET
	public List<User> getUsers(@BeanParam UserFilterBean filterBean, @Context UriInfo uriInfo) {
		if (filterBean.getYear() != null) {
			List<User> users = userService.filterByYear(filterBean.getYear());
			return getUsersWithLinks(users, uriInfo);
		}
		if (filterBean.getStart() != null && filterBean.getSize() != null) {
			List<User> users = userService.paginatedUsers(filterBean.getStart(), filterBean.getSize());
			return getUsersWithLinks(users, uriInfo);
		}
		if (filterBean.getRoleId() != null) {
			List<User> users = userService.filterByRole(filterBean.getRoleId());
			return getUsersWithLinks(users, uriInfo);
		}
		List<User> users = userService.findAll();
		return getUsersWithLinks(users, uriInfo);
	}

	@GET
	@Path("/{userId}")
	public User getUserById(@PathParam("userId") Long userId, @Context UriInfo uriInfo) throws SQLException {
		User user = userService.findById(userId);
		return setUserLinksAndReturn(uriInfo, user);
	}

	@PUT
	@Path("/{userId}")
	// convertion from json to java obj
	public User updateUser(@PathParam("userId") Long userId, User user, @Context UriInfo uriInfo) {
		user.setUserId(userId);
		userService.update(user);
		return setUserLinksAndReturn(uriInfo, user);
	}

	@DELETE
	@Path("/{userId}")
	public void deleteUser(@PathParam("userId") Long userId) {
		userService.remove(new User(userId));
	}

	private List<User> getUsersWithLinks(List<User> users, UriInfo uriInfo) {
		for (User user : users) {
			setUserLinksAndReturn(uriInfo, user);
		}
		return users;
	}

	private User setUserLinksAndReturn(UriInfo uriInfo, User user) {
		String userUrl = uriInfo.getBaseUriBuilder().path(UserResource.class).path(user.getUserId() + "").build()
				.toString();
		Link linkUser = Link.fromUri(userUrl).rel("user").build();
		if (user.getRole() != null && user.getRole().getRoleId() != null) {
			String roleUrl = uriInfo.getBaseUriBuilder().path(RoleResource.class).path(user.getRole().getRoleId() + "")
					.build().toString();
			Link linkRole = Link.fromUri(roleUrl).rel("role").build();
			user.getRole().setLink(linkRole);
		}
		user.setLink(linkUser);
		return user;
	}
}
