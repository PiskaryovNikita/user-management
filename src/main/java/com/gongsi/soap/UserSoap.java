package com.gongsi.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.gongsi.app.model.User;

@WebService(targetNamespace="com.nixsolutions.wsSoap.user")
public interface UserSoap {
	@WebMethod(operationName = "createUser")
	void createUser(User user);

	@WebMethod(operationName = "updateUser")
	void updateUser(User user);

	@WebMethod(operationName = "removeUser")
	void removeUser(User user);

	@WebMethod(operationName = "findUserByLogin")
	User findUserByLogin(String login);

	@WebMethod(operationName = "findUserByEmail")
	User findUserByEmail(String email);

	@WebMethod(operationName = "findUserById")
	User findUserById(Long userId);

	@WebMethod(operationName = "findAllUsers")
	List<User> findAllUsers();
}
