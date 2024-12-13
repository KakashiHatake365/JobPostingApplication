package com.humber.JobPostingApplication.controllers;

import com.humber.JobPostingApplication.models.Application;
import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.models.MyUser;
import com.humber.JobPostingApplication.services.JobApplicationService;
import com.humber.JobPostingApplication.services.JobService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/jobmasters/employers")
public class EmployerController {
    private final JobService jobService;
    private final JobApplicationService jobApplicationService;

    public EmployerController(JobService jobService, JobApplicationService jobApplicationService) {
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
    }

    //dashboard to go to pages and show where what you have
    @GetMapping("/dashboard")
    public String employerDashboard(Model model) {
        model.addAttribute("jobs", new Job());
        return "fragments/employer/dashboard";
    }

        //Display form to add a new job posting
    @GetMapping("/add-job")
    public String addJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "fragments/employer/add-job";
    }

    // Save a new job posting
    @PostMapping("/add-job")
    public String saveJob(@ModelAttribute Job job, Model model) {
        int statusCode = jobService.saveJob(job);

        if (statusCode == -1) {
            model.addAttribute("error", "Job salary is too low - should be higher than $20,000. Job not added!");
            return "fragments/employer/add-job";
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
        return "fragments/employer/add-job";
    }

    //update a job
    @PostMapping("/update")
    public String updateJob(@PathVariable int id, @ModelAttribute Job job) {
        jobService.updateJob(id, job);
        return "redirect:/jobmasters/jobs/1?message=job-added-successfully!";
    }

    // View jobs created by the employer
    @GetMapping("/view-jobs/{jobId}")
    public String viewEmployerJobs(@PathVariable int jobId, Model model) {
        // Retrieve jobs created by the authenticated employer
        Optional<Job> job = jobService.getJobById(jobId);
        if (job.isPresent()) {
            model.addAttribute("jobs", job.get());
            model.addAttribute("message", "List of your jobs");
            return "fragments/employer/view-employer-jobs"; //to view the job
        } else {
            model.addAttribute("message", "Job not found!");
            return "fragments/auth/error";
        }
    }

    // View applications for a job
    // View applications for a job
    @GetMapping("/view-applications/{id}")
    public String viewJobApplications(@PathVariable int id, @AuthenticationPrincipal MyUser user, Model model) {
        Optional<Job> job = jobService.getJobById(id);
        if (job.isPresent() && job.get().getEmployer().equals(user.getUsername())) {
            // Retrieve applications for the specified job
            List<Application> applications = jobApplicationService.getJobApplicationByJob(id);
            model.addAttribute("applications", applications);
            model.addAttribute("job", job.get());
            return "fragments/employer/view-job-applications";  // View applications for the job
        } else {
            model.addAttribute("error", "You can only view applications for your own jobs.");
            return "fragments/employer/view-employer-jobs";  // Redirect to the employer's jobs view
        }
    }
}
