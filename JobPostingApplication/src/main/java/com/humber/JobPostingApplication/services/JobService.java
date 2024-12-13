package com.humber.JobPostingApplication.services;

import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.repositories.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJob() {return jobRepository.findAll();};
    //save a new job
    public int saveJob(Job job) {
        if (job.getSalary() >= 20000) {
            jobRepository.save(job);
            return 1;
        }
        return -1;
    }

    //get All jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    //get a job by id
    public Optional<Job> getJobById(int id) {
        return jobRepository.findById(id);
    }

    //filtered jobs
    public List<Job> getFilteredJobs(String searchedTitle, String searchedLocation, Double searchedSalary){
        return jobRepository.findByTitleIgnoreCaseAndLocationIgnoreCaseAndSalary(searchedTitle, searchedLocation, searchedSalary);
    }

    //pagination jobs
    public Page<Job> getPaginatedJobs(int pageNo, int pageSize, String sortField, String sortDirection) {
        // sort
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
        return jobRepository.findAll(pageable);
    }

    //updating an existing job
    public Job updateJob(int id, Job updatedJob) {
        //Check if the job exists
        Optional<Job> existingJob = jobRepository.findById(id);
        if (existingJob.isPresent()) {
            Job jobToUpdate = existingJob.get();
            jobToUpdate.setTitle(updatedJob.getTitle());
            jobToUpdate.setDescription(updatedJob.getDescription());
            jobToUpdate.setRequirements(updatedJob.getRequirements());
            jobToUpdate.setLocation(updatedJob.getLocation());
            jobToUpdate.setSalary(updatedJob.getSalary());
            return jobRepository.save(jobToUpdate);
        } else {
            throw new RuntimeException("Job not found");
        }
    }

    //delete job by id
    public int deleteJobById(int id) {
        if(jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return 1;
        }
        //delete failure - job not found
        return -1;
    }
}
