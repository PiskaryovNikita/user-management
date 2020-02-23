package com.gongsi.app.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.gongsi.app.controller.RegistrationController;
import com.gongsi.app.persistence.model.User;
import com.gongsi.app.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerTest {
	@Mock
	private UserService userDao;
	@Mock
	private Validator userValidator;
	@InjectMocks
	private RegistrationController controller;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetRegistrationPage() throws Exception {
		this.mockMvc.perform(get("/registr")).andExpect(status().isOk()).andExpect(forwardedUrl("Registration"))
				.andExpect(model().attributeExists("user"));
	}

	@Test
	public void testPostInvalidUser() throws Exception {
		mockMvc.perform(post("/registr").param("login", "login1").param("password", "mvcpassword")
				.param("email", "mvcemail@test.com")
				.param("firstName", "mvcfirst").param("lastName", "lastName1")
				.param("birthday", "")
				.param("role", "User"))
		.andExpect(status().isOk())
		.andExpect(forwardedUrl("Registration"))
		.andExpect(model().attributeExists("user"))
		.andExpect(model().attributeHasErrors("user"))
		.andExpect(model().attributeHasFieldErrors("user", "birthday"));
		verify(userValidator, times(1)).validate(any(User.class), any(BindingResult.class));
	}

	@Test
	public void testPostValidUser() throws Exception {
		mockMvc.perform(post("/registr").param("login", "mvclogin").param("password", "mvcpassword")
				.param("passwordAgain", "mvcpassword").param("email", "mvcemail@test.com")
				.param("firstName", "mvcfirst").param("lastName", "mvclastname")
				.param("birthday", "1999-12-12")
				.param("role", "User"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"))
		.andExpect(model().attributeExists("user"));
		InOrder inOrder = inOrder(userValidator, userDao);
		inOrder.verify(userValidator, times(1)).validate(any(User.class), any(BindingResult.class));
		inOrder.verify(userDao, times(1)).create(any(User.class));
	}

	@Test
	public void testWrongUrl() throws Exception {
		mockMvc.perform(get("/xxx"))
		.andExpect(status().isNotFound());
	}
}
