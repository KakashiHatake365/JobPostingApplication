package com.humber.JobPostingApplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);

        return "fragments/auth/login";
    }
}
