package com.humber.JobPostingApplication.repositories;

import com.humber.JobPostingApplication.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByEmployerId(Long employerId);
    List<Job> findByTitleContaingIgnoreCase(String title);
}
