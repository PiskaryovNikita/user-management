package com.gongsi.app.controller;

import java.security.Principal;
import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.persistence.model.User;
import com.gongsi.app.service.UserService;

@Controller
public class RegistrationController {
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("userValidator")
	private Validator userValidator;

	@RequestMapping(method = RequestMethod.GET, value = "/registr")
	public String getRegistrationPage(Principal principal, Model model) throws SQLException {
		model.addAttribute("user", new User());
		return "Registration";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/registr")
	public String registr(@Valid User user, BindingResult bindingResult, Model model) throws SQLException {
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return "Registration";
		}
		user.setRole(new Role(1l, "User"));
		userService.create(user);
		return "redirect:/login";
	}
}