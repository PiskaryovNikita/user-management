package com.gongsi.app.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.persistence.model.User;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/userDataset/initialDataUserDaoTest.xml")
@DatabaseTearDown(type = com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL, value = "/userDataset/initialDataUserDaoTest.xml")
@DbUnitConfiguration(databaseConnection = "dataSource")
public class SpringUserTest {
    @Autowired
    private UserService userService;

    @Test
    @ExpectedDatabase(value = "/userDataset/expDsCreate.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testCreate() throws Exception {
        User userCr = new User("l", "s", "sl", "s", "l", Date.valueOf("1999-07-07"), Role.ADMIN);
        userService.create(userCr);
    }

    @Test
    @ExpectedDatabase(value = "/userDataset/expDsRemove.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testRemove() throws Exception {
        User userDel = new User(30L, "jdjra", "s", "sl", "s", "l", Date.valueOf("1999-07-07"), Role.USER);
        userService.remove(userDel);
    }

    @Test
    @ExpectedDatabase(value = "/userDataset/expDsUpd.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdate() {
        User userUpd = new User(30L, "jdjra", "pass", "djuraemail", "fn", "ln", Date.valueOf("1999-07-07"),
                Role.ADMIN);
        userService.update(userUpd);
    }

    @Test
    public void testFindByEmail() {
        assertNotNull(userService.findByEmail("lsuyun123"));
    }

    @Test
    public void testFindByLogin() {
        assertNotNull(userService.findByLogin("liangsuyun"));
    }

    @Test
    public void testFindAll() {
        List<User> users = userService.findAll();
        List<User> exp = new ArrayList<>(Arrays.asList(
                new User(10L, "liangsuyun", "qwerty", "lsuyun123", "Suyun", "Liang", Date.valueOf("1998-08-06"),
                        Role.ADMIN),
                new User(20L, "npiskaryov", "12345", "nikita321", "Nikita", "Piskaryov", Date.valueOf("1999-01-15"),
                        Role.ADMIN),
                new User(30L, "jdjra", "zxc", "edjura987", "Eugene", "Djura", Date.valueOf("1999-07-07"),
                        Role.USER)));
        assertNotNull(users);
        assertEquals(exp, users);
    }

    @Test(expected = RuntimeException.class)
    public void testFkViolation() {
        userService.create(new User(4L, "jdjra", "s", "sl", "s", "l", new Date(0), Role.USER));
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateWhenUserDoesNotExist() {
        userService.update(new User(1L, "liangsuyunx123", "qwerty", "lsuyun123", "Suyun", "Liang",
                Date.valueOf("1998-08-06"), Role.ADMIN));
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteWhenUserDoesNotExist() {
        userService.remove(new User(4L, "zzz", "s", "sl", "s", "l", new Date(0), Role.ADMIN));
    }
}
