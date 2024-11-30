package com.humber.JobPostingApplication.controllers;

import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.services.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/jobmasters")
public class JobController {
    //constuctor injection
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    //Display all job posting
    @GetMapping("/jobs")
    public String listJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "list-jobs";
    }

    //display a single job posting by ID
    @GetMapping("/jobs/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id).orElse(null);
        model.addAttribute("job", job);
        return "view-job";
    }
}
