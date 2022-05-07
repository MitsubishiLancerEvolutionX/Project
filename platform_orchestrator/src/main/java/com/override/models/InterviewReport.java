package com.override.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class InterviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(name = "user_login")
    private String userLogin;

    private String company;

    private String project;

    private String questions;

    private Integer impression;

    @Column(name = "min_salary")
    private String minSalary;

    @Column(name = "max_salary")
    private String maxSalary;

    private String status;

    private String level;
}