package com.humber.JobPostingApplication.controllers;

import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.models.MyUser;
import com.humber.JobPostingApplication.services.JobService;
import com.humber.JobPostingApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/jobmasters/admin")
public class AdminController {
    //constructor injections
    private final UserService userService;

    //get job posting name
    @Value("JOBMASTERS")
    private String postingName;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // Admin dashboard - overview of users, jobs, and applications
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<MyUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "fragments/admin/dashboard";
    }

    // Manage user roles (e.g., admin or employer)
    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        List<MyUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "fragments/admin/manage-users";
    }

    // Change user role (example: change to admin or employer)
    @PostMapping("/change-role/{userId}")
    public String changeUserRole(@PathVariable Long userId, @RequestParam String role, Model model, @ModelAttribute MyUser user) {
        userService.changeUserRole(userId, role);
        model.addAttribute("users", user);
        model.addAttribute("message", "User role updated successfully!");
        return "redirect:fragments/admin/manage-users";
    }

    // View system-wide reports (jobs, applications, etc.)
    @GetMapping("/reports")
    public String viewReports(Model model) {
        // Logic to fetch system-wide reports (e.g., total jobs, total applications)
        model.addAttribute("report", "System-wide reports will be here");
        return "fragments/admin/reports";
    }
}
