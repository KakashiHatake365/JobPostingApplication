package com.humber.JobPostingApplication;

import com.humber.JobPostingApplication.models.Job;
import com.humber.JobPostingApplication.services.JobService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobPostingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JobPostingApplication.class, args);
	}

	private JobService jobService;

	public JobPostingApplication(JobService jobService) {
		this.jobService = jobService;
	}

	@Override
	public void run(String... args) throws Exception {
		jobService.saveJob(new Job(1, "Cashier", "Cashier needed for work! A lot of perks", "No Experience", "Mississauga, ON", 25000, "McDonalds"));
		jobService.saveJob(new Job(2, "Chef", "Chef needed! Avaiable anytime and work long hours due to low staff!", "5 years", "Brampton, ON", 30000, "Hilton Brampton"));
	}
}
