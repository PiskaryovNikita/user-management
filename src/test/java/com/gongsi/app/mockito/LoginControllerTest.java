package com.gongsi.app.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.gongsi.app.controller.LoginController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest {
	@InjectMocks
	private LoginController controller;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetLoginPage() throws Exception {
		this.mockMvc.perform(get("/login"))
		.andExpect(status().isOk())
		.andExpect(forwardedUrl("LoginPage"));
	}

	@Test
	public void testLogout() throws Exception {
		mockMvc.perform(get("/logout"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/login"));
	}

	@Test
	public void testWrongUrl() throws Exception {
		mockMvc.perform(get("/xxx"))
		.andExpect(status().isNotFound());
	}
}