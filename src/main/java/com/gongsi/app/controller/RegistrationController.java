package com.gongsi.app.controller;

import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.persistence.model.User;
import com.gongsi.app.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("userValidator")
    private Validator userValidator;

    @RequestMapping(method = RequestMethod.GET, value = "/registr")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "Registration";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/registr")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "Registration";
        }
        user.setRole(Role.USER);
        userService.create(user);
        return "redirect:/login";
    }
}