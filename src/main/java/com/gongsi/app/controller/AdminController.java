package com.gongsi.app.controller;

import com.gongsi.app.config.jmx.LoggingController;
import com.gongsi.app.persistence.model.Role;
import com.gongsi.app.persistence.model.User;
import com.gongsi.app.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("userValidator")
    private Validator userValidator;
    @Autowired
    private LoggingController loggingController;

    @GetMapping({"/", "/home"})
    public String showHome(Principal principal, Model model) {
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (NotFoundException exception) {
            return "redirect:/login";
        }
        if (user.getRole().equals(Role.ADMIN)) {
            List<User> users = userService.findAll();

            if (loggingController.isEnabled()) {
                log.info("Selected size of users list: {}", users.size());
            }
            
            model.addAttribute("users", users);

            log.trace("Model attributes: {}", model.asMap());

            return "AdminHomePage";
        } else {
            return "UserHomePage";
        }
    }

    @GetMapping("/adminDelete")
    public String adminDelete(@RequestParam("id") Long id) {
        userService.remove(new User(id));
        return "redirect:/home";
    }

    @GetMapping("/adminEdit")
    public String editPage(Model model, @RequestParam("id") Long id) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", Role.values());
        return "AdminEditPage";
    }

    @PostMapping("/adminEdit")
    public String adminEdit(@Valid User user, BindingResult bindingResult, Model model,
                            @RequestParam("role") String role) {
        user.setRole(Role.valueOf(role));
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            return "AdminEditPage";
        }
        userService.update(user);
        return "redirect:/home";
    }

    @GetMapping("/adminAdd")
    public String addPage(Model model) {
        model.addAttribute("user", new User(Role.USER));
        model.addAttribute("roles", Role.values());
        return "AdminAddPage";
    }

    @PostMapping("/adminAdd")
    public String adminAdd(@Valid User user, BindingResult bindingResult, Model model,
                           @RequestParam("role") String role) {
        userValidator.validate(user, bindingResult);
        user.setRole(Role.valueOf(role));
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            return "AdminAddPage";
        }
        userService.create(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/home";
    }
}
