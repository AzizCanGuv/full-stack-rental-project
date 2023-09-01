package com.comp3102.backend.jobApplication.dto.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("jobApplication")
public class JobApplicationRQ {
    @NotBlank(message = "First Name cannot be null")
    private String firstName;
    @NotBlank(message = "Last Name cannot be null")
    private String lastName;
    @NotBlank(message = "Email cannot be null")
    private String email;
    private LocalDateTime birthDate;
    @NotBlank(message = "Phone Number cannot be null")
    private String phoneNumber;
    @NotBlank(message = "School cannot be null")
    private String school;
    @NotNull(message = "GPA cannot be null")
    private Double gpa;
}
