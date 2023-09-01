package com.comp3102.backend.jobApplication;

import com.comp3102.backend.core.exceptions.EntityNotFoundException;
import com.comp3102.backend.core.mailConfiguration.EmailSenderService;
import com.comp3102.backend.jobApplication.dto.converter.JobApplication2JobApplicationRSConverter;
import com.comp3102.backend.jobApplication.dto.converter.JobApplicationRQ2JobApplicationConverter;
import com.comp3102.backend.jobApplication.dto.request.JobApplicationRQ;
import com.comp3102.backend.jobApplication.dto.response.JobApplicationRS;
import com.comp3102.backend.s3.S3Service;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.comp3102.backend.core.constant.EmailConstants.jobApplicationEmailBodyParser;
import static com.comp3102.backend.core.constant.ErrorConstant.*;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationRQ2JobApplicationConverter jobApplicationRQ2JobApplicationConverter;
    private final JobApplication2JobApplicationRSConverter jobApplication2JobApplicationRSConverter;
    private final S3Service s3Service;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.region}")
    private String bucketRegion;
    @Value("${aws.s3.general.url.pattern}")
    private String bucketGeneralUrl;
    @Value("${aws.s3.job.application.end.point}")
    private String jobApplicationEndPoint;

    private final EmailSenderService mailService;

    public JobApplicationRS addNewJobApplication(JobApplicationRQ jobApplicationRQ, MultipartFile file) throws MessagingException {
        JobApplication jobApplication = jobApplicationRQ2JobApplicationConverter.convert(jobApplicationRQ);
        var jobApplicationId = jobApplicationRepository.save(jobApplication).getJobApplicationId();
        jobApplicationRepository.findById(jobApplicationId);
        if (file != null) {
            uploadJobApplicationFile(jobApplicationId, file);
        }
        return jobApplication2JobApplicationRSConverter.convert(jobApplication);
    }

    public void uploadJobApplicationFile(UUID jobApplicationId, MultipartFile file) throws MessagingException {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(JOB_NOT_FOUND_MESSAGE, String.valueOf(jobApplicationId))));
        String jobApplicationFileId = UUID.randomUUID().toString();
        try {
            s3Service.putObjectPdf(
                    bucketName,
                    jobApplicationEndPoint.formatted(jobApplicationId, jobApplicationFileId),
                    file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String pictureUrl = String.format("%s%s",bucketGeneralUrl, jobApplicationEndPoint).formatted(bucketName, bucketRegion, jobApplicationId, jobApplicationFileId);
        jobApplication.setJobApplicationFileId(pictureUrl);
        jobApplicationRepository.save(jobApplication);
        mailService.sendJobApplicationMail(jobApplication.getFirstName(), jobApplication.getLastName(), jobApplicationEmailBodyParser(jobApplication));
    }

    public JobApplicationRS getJobApplicationById(UUID id) {
        JobApplication jobApplication = jobApplicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(JOB_NOT_FOUND_MESSAGE, String.valueOf(id))));
        return jobApplication2JobApplicationRSConverter.convert(jobApplication);
    }

    public List<JobApplicationRS> getAllJobApplications() {
        return jobApplication2JobApplicationRSConverter.convert(jobApplicationRepository.findAll());
    }
}
