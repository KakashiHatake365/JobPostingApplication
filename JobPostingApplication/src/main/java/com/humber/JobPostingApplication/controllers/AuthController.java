package com.humber.JobPostingApplication.controllers;

import com.humber.JobPostingApplication.models.MyUser;
import com.humber.JobPostingApplication.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController implements org.springframework.boot.web.servlet.error.ErrorController {
    private final UserService userService;

    // get the application name
    @Value("${POSTING_NAME}")
    private String name;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //error endpoint
    @GetMapping("/error")
    public String error() {
        return "fragments/auth/error";
    }

    //login endpoint
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("postingName", name);

        return "fragments/auth/login";
    }

    //Logout endpoint
    @GetMapping("/logout")
    public String customLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //logout logic
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login?message=You have been logged out!";
    }

    //register endpoint (open to registration view
    @GetMapping("/register")
    public String register(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("message", message);
        model.addAttribute("user", new MyUser()); //creating a new user under the MyUser variable
        return "fragments/auth/register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute MyUser user) {
        //saves the user to the database
        int saveUserCode = userService.saveUser(user);

        if (saveUserCode == 0) {
            return "redirect:/register?message=Registration Failed! Username already exists!";
        } else {
            return "redirect:/login?message=Registration Successful!";
        }
    }
}
