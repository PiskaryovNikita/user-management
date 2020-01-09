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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gongsi.app.model.Role;
import com.gongsi.app.model.User;
import com.gongsi.app.service.RoleService;
import com.gongsi.app.service.UserService;

@Controller
public class AdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	@Qualifier("userValidator")
	private Validator userValidator;

	@GetMapping({ "/", "/home" })
	public String showHome(Principal principal, Model model) throws SQLException {
		String login = principal.getName();
		User user = userService.findByLogin(login);
		if (user.getRole().getRoleId() == 1l) {
			return "UserHomePage" ;
		} else {
			model.addAttribute("users", userService.findAll());
			return "AdminHomePage";
		}
	}

	@GetMapping("/adminDelete")
	public String adminDelete(Principal principal, Model model, @RequestParam("userId") Long userId)
			throws SQLException {
		userService.remove(new User(userId));
		return "redirect:/home";
	}

	@GetMapping("/adminEdit")
	public String editPage(Model model, @RequestParam("userId") Long userId) throws SQLException {
		model.addAttribute("user", userService.findById(userId));
		model.addAttribute("roles", roleService.findAll());
		return "AdminEditPage";
	}

	@PostMapping("/adminEdit")
	public String adminEdit(@Valid User user, BindingResult bindingResult, Model model,
			@RequestParam("role") String role) throws SQLException {
		user.setRole(roleService.findByName(role));
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("roles", roleService.findAll());
			return "AdminEditPage";
		}
		userService.update(user);
		return "redirect:/home";
	}

	@GetMapping("/adminAdd")
	public String addPage(Model model) throws SQLException {
		model.addAttribute("user", new User(new Role(1l, "User")));
		model.addAttribute("roles", roleService.findAll());
		return "AdminAddPage";
	}

	@PostMapping("/adminAdd")
	public String adminAdd(@Valid User user, BindingResult bindingResult, Model model, @RequestParam("role") String role)
			throws SQLException {
		userValidator.validate(user, bindingResult);
		user.setRole(roleService.findByName(role));
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("roles", roleService.findAll());
			return "AdminAddPage";
		}
		userService.create(user);
		model.addAttribute("users", userService.findAll());
		return "redirect:/home";
	}
}
