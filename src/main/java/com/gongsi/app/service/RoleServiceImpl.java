package com.gongsi.app.service;

import com.gongsi.app.errorHandling.exceptions.DataNotFoundExcpetion;
import com.gongsi.app.errorHandling.exceptions.ResourceAlreadyExistsException;
import com.gongsi.app.persistence.RoleDao;
import com.gongsi.app.persistence.model.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public void create(Role role) {
        if (roleDao.findByName(role.getName()) != null) {
            throw new ResourceAlreadyExistsException(role + " already in db");
        }
        roleDao.create(role);
    }

    @Override
    @Transactional
    public void update(Role role) {
        if (roleDao.findById(role.getId()) == null) {
            throw new DataNotFoundExcpetion("resource " + role + " not found");
        }
        // excess entry in child table
        if (roleDao.findByName(role.getName()) != null) {
            throw new ResourceAlreadyExistsException(role.toString() + " entry with this name already in db");
        }
        roleDao.update(role);
    }

    @Override
    @Transactional
    public void remove(Role role) {
        // upd count = 0
        if (findById(role.getId()) == null) {
            throw new DataNotFoundExcpetion(role.toString() + " doesn't exist in DB");
        }
        roleDao.remove(role);
    }

    @Override
    public Role findById(Long roleId) {
        Role role = roleDao.findById(roleId);
        if (role == null) {
            throw new DataNotFoundExcpetion("no role with id " + roleId);
        }
        return role;
    }

    @Override
    @Transactional
    public Role findByName(String name) {
        Role role = roleDao.findByName(name);
        if (role == null) {
            throw new DataNotFoundExcpetion("no role with name " + name);
        }
        return role;
    }

    @Override
    @Transactional
    public List<Role> findAll() {
        List<Role> roles = roleDao.findAll();
        if (Objects.isNull(roles)) {
            roles = new ArrayList<>();
        }
        return roles;
    }

}
