package com.comp3102.backend.jobApplication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "job_application")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID jobApplicationId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthDate;
    private String phoneNumber;
    private String school;
    private Double gpa;
    private LocalDateTime postedAt;
    private String jobApplicationFileId;
}
