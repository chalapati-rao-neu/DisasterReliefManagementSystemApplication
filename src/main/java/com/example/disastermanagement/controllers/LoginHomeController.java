package com.example.disastermanagement.controllers;

import com.example.disastermanagement.models.User;
import com.example.disastermanagement.models.Role;
import com.example.disastermanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginHomeController {

    private final UserService userService;

    @Autowired
    public LoginHomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // Set default role to USER for all new signups
        user.setRole(Role.USER);

        // Save the user with password encryption
        userService.createUser(user);

        return "redirect:/login?signupSuccess";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}