package com.humber.JobPostingApplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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
