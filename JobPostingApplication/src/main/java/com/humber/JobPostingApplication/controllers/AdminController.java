package com.humber.JobPostingApplication.controllers;

import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/jobmasters/admin")
public class AdminController {
    //constructor injections
    private final JobService jobService;

    //get job posting name
    @Value("JOBMASTERS")
    private String postingName;

    @Autowired
    public AdminController(JobService jobService) {
        this.jobService = jobService;
    }

    //Display form to add a new job posting
    @GetMapping("/add-job")
    public String addJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "fragments/admin/add-job";
    }

    // Save a new job posting
    @PostMapping("/add-job")
    public String saveJob(@ModelAttribute Job job, Model model) {
        int statusCode = jobService.saveJob(job);

        if (statusCode == -1) {
            model.addAttribute("error", "Job salary is too low - should be higher than $20,000. Job not added!");
            return "fragments/admin/add-job";
        }
        return "redirect:/jobmasters/jobs/1?message=job-added-successfully!";
    }

    // Delete a job by ID
    @GetMapping("/delete/{id}")
    public String deleteJobById(@PathVariable int id) {
        jobService.deleteJobById(id);
        return "redirect:/jobmasters/jobs/1?message=Job deleted successfully!";
    }

    //Display form to update a job
    @GetMapping("/update/{id}")
    public String updateJobById(@PathVariable int id, Model model) {
        Optional<Job> jobToUpdate;
        jobToUpdate = jobService.getJobById(id);
        model.addAttribute("job", jobToUpdate.orElse(new Job()));
        return "fragments/admin/add-job";
    }

    //update a job
    @PostMapping("/update")
    public String updateJob(@PathVariable int id, @ModelAttribute Job job) {
        jobService.updateJob(id, job);
        return "redirect:/jobmasters/jobs/1?message=Job updated successfully!";
    }
}
