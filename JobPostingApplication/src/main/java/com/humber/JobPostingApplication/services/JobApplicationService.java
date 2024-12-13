package com.humber.JobPostingApplication.services;

import com.humber.JobPostingApplication.models.Application;
import com.humber.JobPostingApplication.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {
    private final ApplicationRepository applicationRepository;

    public JobApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    //Save a new job Application
    public Application saveJobApplication(Application application) {
        return applicationRepository.save(application);
    }

    //Get All Job application
    public List<Application> getAllJobApplications() {
        return applicationRepository.findAll();
    }

    //Get job applications by user (seeker)
    public List<Application> getJobApplicationByUser(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    //get job applications for specific job
    public List<Application> getJobApplicationByJob(int jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    //Get job application by id
    public Optional<Application> getJobApplicationById(int id) {
        return applicationRepository.findById(id);
    }

    //Update an existing job application
    public Application updateJobApplication(int id, Application updatedApplication) {
        //Check if the job application exists
        Optional<Application> existingApplication = applicationRepository.findById(id);
        if (existingApplication.isPresent()) {
            Application applicationToUpdate = existingApplication.get();
            applicationToUpdate.setCoverLetter(updatedApplication.getCoverLetter());
            applicationToUpdate.setResume(updatedApplication.getResume());
            // Other fields to update can be added here
            return applicationRepository.save(applicationToUpdate);
        }
        throw new RuntimeException("Job application not found");
    }


    // Delete a job application by id
    public int deleteJobApplicationById(int id) {
        if (applicationRepository.existsById(id)) {
            applicationRepository.deleteById(id);
            return 1;
        }
        // Return failure code if job application is not found
        return -1;
    }
}
