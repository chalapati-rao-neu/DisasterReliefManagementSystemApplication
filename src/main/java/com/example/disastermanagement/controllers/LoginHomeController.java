package com.example.disastermanagement.controllers;

import com.example.disastermanagement.models.User;
import com.example.disastermanagement.models.Role;
import com.example.disastermanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginHomeController {

    private final UserService userService;

    @Autowired
    public LoginHomeController(UserService userService) {
        this.userService = userService;
    }

    // Login Page
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "signupSuccess", required = false) String signupSuccess,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        if (signupSuccess != null) {
            model.addAttribute("signupMessage", "Signup successful! Please log in.");
        }
        return "login";
    }

    // Signup Form Page
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Handle Signup Submission
    @PostMapping("/signup")
    public String signupUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (userService.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists. Please choose a different one.");
            return "signup";
        }

        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists. Please choose a different one.");
            return "signup";
        }

        user.setRole(Role.USER);
        userService.createUser(user);

        return "redirect:/login?signupSuccess=true";
    }

    // Home Page
    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", authentication.getName());
        return "home";
    }
}