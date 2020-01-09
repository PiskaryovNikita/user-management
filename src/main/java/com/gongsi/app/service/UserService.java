package com.gongsi.app.service;

import java.util.List;

import com.gongsi.app.model.User;

public interface UserService {
	void create(User user);
	void update(User user);
	void remove(User user);
	User findByLogin(String login);
	User findByEmail(String email);
	User findById(Long userId);
	List<User> filterByYear(int year);
	List<User> filterByRole(Long roleId);
	List<User> paginatedUsers(int start, int size);
	List<User> findAll();
}
