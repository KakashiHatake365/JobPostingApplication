package com.humber.JobPostingApplication.controllers;

import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.services.JobService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/jobmasters")
public class JobController {
    //constuctor injection
    private final JobService jobService;

    //get Job Posting name
    @Value("${POSTING_NAME}")
    private String postingName;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Value("5")
    private int pageSize;

    //Home page
    @GetMapping("/home")
    public String Home(Model model) {
        model.addAttribute("rName", postingName);
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
        model.addAttribute("Jobs", page.getContent());
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

    //display a single job posting by ID
    @GetMapping("/jobs/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id).orElse(null);
        model.addAttribute("job", job);
        return "view-jobs";
    }
}
