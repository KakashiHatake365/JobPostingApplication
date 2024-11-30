package com.humber.JobPostingApplication.controllers;

import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/jobmasters/admin")
public class AdminController {
    //constructor injections
    private final JobService jobService;

    @Autowired
    public AdminController(JobService jobService) {
        this.jobService = jobService;
    }

    //Display form to add a new job posting
    @GetMapping("/add-job")
    public String addJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "admin/add-job";
    }

    // Save a new job posting
    @PostMapping("/add-job")
    public String saveJob(@ModelAttribute Job job, Model model) {
        //if job salary is less than 20000 than it would fail but if its higher than it will add
        if (job.getSalary() < 20000) {
            model.addAttribute("error", "Job salary is way too low. Must be higher than $20,000");
            return "admin/add-job";
        } else {
            jobService.saveJob(job);
            return "redirect:/admin?message=Job added successfully!";
        }
    }

    // Delete a job by ID
    @GetMapping("/delete/{id}")
    public String deleteJobById(@PathVariable Long id) {
        jobService.deleteJobById(id);
        return "redirect:/admin?message=Job deleted successfully!";
    }

    //Display form to update a job
    @GetMapping("/update/{id}")
    public String updateJobById(@PathVariable Long id, Model model) {
        Optional<Job> jobToUpdate = jobService.getJobById(id);
        model.addAttribute("job", jobToUpdate.orElse(new Job()));
        return "admin/update-job";
    }

    //update a job
    @PostMapping("/update")
    public String updateJob(@PathVariable Long id, @ModelAttribute Job job) {
        jobService.updateJob(id, job);
        return "redirect:/admin?message=Job updated successfully!";
    }
}
