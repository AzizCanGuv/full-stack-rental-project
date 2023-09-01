package com.comp3102.backend.jobApplication;

import com.comp3102.backend.jobApplication.dto.request.JobApplicationRQ;
import com.comp3102.backend.jobApplication.dto.response.JobApplicationRS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/job-applications")
@RequiredArgsConstructor
@CrossOrigin
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            description = "Add a new job application",
            summary = "Add new job application"
    )
    public ResponseEntity<JobApplicationRS> addNewJobApplication(@RequestPart("jobApplication") @Valid JobApplicationRQ jobApplicationRS,
                                                                 @RequestPart("file") MultipartFile file) throws MessagingException {
        return new ResponseEntity<>(jobApplicationService.addNewJobApplication(jobApplicationRS, file), HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    @Operation(
            description = "Get a job application by ID",
            summary = "Get job application by ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<JobApplicationRS> getJobApplicationById(@PathVariable UUID id) {
        return new ResponseEntity<>(jobApplicationService.getJobApplicationById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @Operation(
            description = "Get all job applications for a specific ID",
            summary = "Get all job applications",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<JobApplicationRS>> getAllJobApplications() {
        return new ResponseEntity<>(jobApplicationService.getAllJobApplications(), HttpStatus.OK);
    }

}
