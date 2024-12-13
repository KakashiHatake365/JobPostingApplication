package com.humber.JobPostingApplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    private String coverLetter;
    private String resume;

    private String employer;
}
