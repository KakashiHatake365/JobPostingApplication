package com.humber.JobPostingApplication.services;

import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    //save a new job
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    //get All jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    //get a job by id
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    //updating an existing job
    public Job updateJob(Long id, Job updatedJob) {
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
    public int deleteJobById(Long id) {
        if(jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return 1;
        }
        //delete failure - job not found
        return -1;
    }
}
