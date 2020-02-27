package com.gongsi.app.service;

import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.persistence.model.User;
import java.util.List;

public interface UserService {
    void create(User user);

    void update(User user);

    void remove(User user);

    User findByLogin(String login);

    User findByEmail(String email);

    User findById(Long userId);

    List<User> filterByYear(int year);

    List<User> filterByRole(Role role);

    List<User> paginatedUsers(int start, int size);

    List<User> findAll();
}
