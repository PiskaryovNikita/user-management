package com.gongsi.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gongsi.app.model.User;
import com.gongsi.app.persistence.UserDao;
import com.gongsi.rest.errorHandling.exceptions.DataNotFoundExcpetion;
import com.gongsi.rest.errorHandling.exceptions.ResourceAlreadyExistsException;

@Service
//1 logical operation - 1 tx
//mb complex dml operations
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public void create(User user) {
		if (userDao.findByLogin(user.getLogin()) != null || userDao.findByEmail(user.getEmail()) != null) {
			throw new ResourceAlreadyExistsException(user + " already in db");
		}
		userDao.create(user);
	}

	@Override
	@Transactional
	public void update(User user) {
		// upd count = 0
		if (findById(user.getUserId()) == null) {
			throw new DataNotFoundExcpetion(user + " doesn't exist");
		}
		userDao.update(user);
	}

	@Override
	@Transactional
	public void remove(User user) {
		// delete count = 0
		if (findById(user.getUserId()) == null) {
			throw new DataNotFoundExcpetion(user + " doesn't exist");
		}
		userDao.remove(user);
	}

	@Override
	public List<User> filterByRole(Long roleId) {
		List<User> users = new ArrayList<>();
		for (User user : findAll()) {
			if (user.getRole().getRoleId() == roleId) {
				users.add(user);
			}
		}
		return users;
	}

	@Override
	public List<User> filterByYear(int year) {
		List<User> users = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		for (User user : findAll()) {
			calendar.setTime(user.getBirthday());
			if (calendar.get(Calendar.YEAR) == year) {
				users.add(user);
			}
		}
		return users;
	}

	@Override
	public List<User> paginatedUsers(int start, int size) {
		List<User> users = findAll();
		if (start + size > users.size()) {
			return new ArrayList<>();
		}
		return users.subList(start, start + size);
	}

	@Override
	@Transactional
	public User findById(Long userId) {
		User user = userDao.findById(userId);
		if (user == null) {
			throw new DataNotFoundExcpetion("no user with id " + userId);
		}
		return user;
	}

	@Override
	@Transactional
	public User findByLogin(String login) {
		User user = userDao.findByLogin(login);
		if (user == null) {
			throw new DataNotFoundExcpetion("no user with login " + login);
		}
		return user;
	}

	@Override
	@Transactional
	public User findByEmail(String email) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			throw new DataNotFoundExcpetion("no user with email " + email);
		}
		return user;
	}

	@Override
	@Transactional
	public List<User> findAll() {
		List<User> users = userDao.findAll();
		if (users.size() == 0) {
			throw new DataNotFoundExcpetion("no users");
		}
		return users;
	}
}
