package com.gongsi.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(method = RequestMethod.GET, value = { "/login" })
	public String showLoginPage(HttpSession session) {
		return "LoginPage";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/logout" })
	public String logout() {
		return "redirect:/login";
	}
}
