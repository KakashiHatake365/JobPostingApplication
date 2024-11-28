package com.humber.JobPostingApplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private MyUser employer;
}
