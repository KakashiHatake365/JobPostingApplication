package com.humber.JobPostingApplication.controllers;

import com.humber.JobPostingApplication.models.Application;
import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.services.JobApplicationService;
import com.humber.JobPostingApplication.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jobmasters")
public class JobController {
    //constuctor injection
    private final JobService jobService;
    private final JobApplicationService jobApplicationService;

    //get Job Posting name
    @Value("${POSTING_NAME}")
    private String postingName;

    @Autowired
    public JobController(JobService jobService, JobApplicationService jobApplicationService) {
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
    }

    @Value("5")
    private int pageSize;

    //Home page
    @GetMapping("/home")
    public String Home(Model model) {
        model.addAttribute("pName", postingName);
        return "home";
    }

    //Display all job posting
    @GetMapping("/jobs/{pageNo}")
    public String listJobs(Model model, @RequestParam(required = false) String searchedTitle,
                           @RequestParam(required = false) String searchedLocation,
                           @RequestParam(required = false) Double searchedSalary,
                           @PathVariable int pageNo,
                           @RequestParam(required = false,defaultValue = "id") String sortField,
                           @RequestParam(required = false, defaultValue = "asc") String sortDirection) {

        // filtering logic
        if (searchedTitle != null || searchedLocation != null || searchedSalary != null) {
            List<Job> filteredJobs = jobService.getFilteredJobs(searchedTitle, searchedLocation, searchedSalary);
            model.addAttribute("jobs", filteredJobs);
            model.addAttribute("message", !filteredJobs.isEmpty() ? "Job filtered!" : "No job found!");
            return "list-jobs";
        }

        //pagination logic and the info to the model.
        Page<Job> page = jobService.getPaginatedJobs(pageNo, pageSize, sortField, sortDirection);
        model.addAttribute("jobs", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());

        // sorting info
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("message", "List of all jobs");

        return "list-jobs";
    }

    @GetMapping("/view-jobs/{jobId}")
    public String viewJobs(@PathVariable int jobId, Model model ) {
        Optional<Job> job = jobService.getJobById(jobId);
        if (job.isPresent()) {
            model.addAttribute("jobs", job.get());
            return "view-job"; //to view the job
        } else {
            model.addAttribute("message", "Job not found!");
            return "fragments/auth/error";
        }
    }

    //Apply for job
    @GetMapping("/apply/{jobId}")
    public String applyJob(@PathVariable int jobId, Model model) {
        Optional<Job> job = jobService.getJobById(jobId);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
            return "apply-job";
        } else {
            model.addAttribute("message", "Job not found!");
            return "fragments/auth/error";
        }
    }

    //Handle the job application submission
    @PostMapping("/apply/{jobId}")
    public String submitJobApplication(@PathVariable int jobId, Application application, Model model) {
        jobApplicationService.updateJobApplication(jobId, application);
        model.addAttribute("message", "Your application has been submitted!");
        return "list-jobs";
    }
}
