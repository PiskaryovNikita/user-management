package com.gongsi.soap;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.gongsi.app.model.User;
import com.gongsi.app.service.UserService;

@WebService(endpointInterface="com.nixsolutions.wsSoap.UserSoap", serviceName="UserService", targetNamespace="com.nixsolutions.wsSoap.user")
public class UserSoapImpl extends SpringBeanAutowiringSupport implements UserSoap {
	@Autowired
	private UserService userService;

	@Override
	public void createUser(User user) {
		userService.create(user);
	}

	@Override
	public void updateUser(User user) {
		userService.update(user);
	}

	@Override
	public void removeUser(User user) {
		userService.remove(user);
	}

	@Override
	public User findUserByLogin(String login) {
		return userService.findByLogin(login);
	}

	@Override
	public User findUserByEmail(String email) {
		return userService.findByEmail(email);
	}

	@Override
	public User findUserById(Long userId) {
		return userService.findById(userId);
	}

	@Override
	public List<User> findAllUsers() {
		return userService.findAll();
	}
}
