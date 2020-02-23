package com.gongsi.app.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.gongsi.app.persistence.model.Role;
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
@DatabaseSetup("/roleDataset/initialDataRoleDaoTest.xml")
@DatabaseTearDown(type = com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL, value = "/roleDataset/initialDataRoleDaoTest.xml")
@DbUnitConfiguration(databaseConnection = "dataSource")
public class SpringRoleTest {
    @Autowired
    private RoleService roleService;

    @Test
    @ExpectedDatabase(value = "/roleDataset/expCreate.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testCreate() {
        roleService.create(new Role("x"));
    }

    @Test
    @ExpectedDatabase(value = "/roleDataset/expDel.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testRemove() {
        roleService.remove(new Role(20L, "admin"));
    }

    @Test
    @ExpectedDatabase(value = "/roleDataset/expUpd.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testUpdate() {
        roleService.update(new Role(10L, "m"));
    }

    @Test
    public void testFind() {
        Role role = roleService.findByName("admin");
        assertNotNull(role);
        assertEquals(20L, (long) role.getId());
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateWhenUserDoesNotExist() {
        roleService.update(new Role(10L, "user"));
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteWhenUserDoesNotExist() {
        roleService.remove(new Role(6L, "xxx"));
    }
}
