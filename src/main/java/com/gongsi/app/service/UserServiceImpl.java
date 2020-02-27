package com.gongsi.app.service;

import com.gongsi.app.errorHandling.exceptions.ResourceAlreadyExistsException;
import com.gongsi.app.persistence.UserDao;
import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.persistence.model.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

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
        if (findById(user.getId()) == null) {
            throw new NotFoundException(user + " doesn't exist");
        }
        userDao.update(user);
    }

    @Override
    @Transactional
    public void remove(User user) {
        if (findById(user.getId()) == null) {
            throw new NotFoundException(user + " doesn't exist");
        }
        userDao.remove(user);
    }

    @Override
    @Transactional
    public List<User> filterByRole(Role role) {
        List<User> users = new ArrayList<>();
        for (User user : findAll()) {
            if (user.getRole().equals(role)) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    @Transactional
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
    @Transactional
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
            throw new NotFoundException("no user with id " + userId);
        }
        return user;
    }

    @Override
    @Transactional
    public User findByLogin(String login) {
        User user = userDao.findByLogin(login);
        if (user == null) {
            throw new NotFoundException("no user with login " + login);
        }
        return user;
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("no user with email " + email);
        }
        return user;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        List<User> users = userDao.findAll();
        if (Objects.isNull(users)) {
            users = new ArrayList<>();
        }
        return users;
    }
}
