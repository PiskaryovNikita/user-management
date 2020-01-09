package com.gongsi.app.persistence;

import java.util.List;

import com.gongsi.app.model.User;

public interface UserDao {
	void create(User user);
	void update(User user);
	void remove(User user);
	List<User> findAll();
	User findByLogin(String login);
	User findByEmail(String email);
	User findById(Long userId);
}
