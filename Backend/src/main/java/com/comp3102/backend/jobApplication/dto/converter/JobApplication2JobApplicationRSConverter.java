package com.comp3102.backend.jobApplication.dto.converter;

import com.comp3102.backend.jobApplication.JobApplication;
import com.comp3102.backend.jobApplication.dto.response.JobApplicationRS;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobApplication2JobApplicationRSConverter {
    public JobApplicationRS convert(JobApplication from) {
        return JobApplicationRS.builder()
                .jobApplicationId(from.getJobApplicationId())
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .birthDate(from.getBirthDate())
                .gpa(from.getGpa())
                .email(from.getEmail())
                .school(from.getSchool())
                .phoneNumber(from.getPhoneNumber())
                .postedAt(LocalDateTime.now())
                .jobApplicationFileId(from.getJobApplicationFileId())
                .build();
    }

    public List<JobApplicationRS> convert(List<JobApplication> from) {
        return from.stream().map(c -> new JobApplicationRS(
                        c.getJobApplicationId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getEmail(),
                        c.getBirthDate(),
                        c.getPhoneNumber(),
                        c.getSchool(),
                        c.getGpa(),
                        c.getPostedAt(),
                        c.getJobApplicationFileId()
                ))
                .collect(Collectors.toList());
    }
}
