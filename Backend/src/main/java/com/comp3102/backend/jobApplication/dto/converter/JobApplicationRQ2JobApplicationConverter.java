package com.comp3102.backend.jobApplication.dto.converter;

import com.comp3102.backend.jobApplication.JobApplication;
import com.comp3102.backend.jobApplication.dto.request.JobApplicationRQ;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobApplicationRQ2JobApplicationConverter {
    public JobApplication convert(JobApplicationRQ from) {
        return JobApplication.builder()
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .birthDate(from.getBirthDate())
                .gpa(from.getGpa())
                .email(from.getEmail())
                .phoneNumber(from.getPhoneNumber())
                .postedAt(LocalDateTime.now())
                .school(from.getSchool())
                .build();
    }
}
