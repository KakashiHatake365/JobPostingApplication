package com.humber.JobPostingApplication.repositories;

import com.humber.JobPostingApplication.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByTitleIgnoreCaseAndLocationIgnoreCaseAndSalary(String title, String location, Double salary);

}
