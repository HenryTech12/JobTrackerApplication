package org.jobtracker.app.JobTracker.service;

import lombok.extern.slf4j.Slf4j;
import org.jobtracker.app.JobTracker.dto.JobDTO;
import org.jobtracker.app.JobTracker.dto.UserDTO;
import org.jobtracker.app.JobTracker.feign.UserFeign;
import org.jobtracker.app.JobTracker.mapper.JobMapper;
import org.jobtracker.app.JobTracker.model.JobModel;
import org.jobtracker.app.JobTracker.repository.JobRepository;
import org.jobtracker.app.JobTracker.response.ErrorResponse;
import org.jobtracker.app.NotificationService.response.InterviewNotificationResponse;
import org.jobtracker.app.JobTracker.response.JobResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private JobMapper jobMapper;

    public JobResponse createJob(JobDTO jobDTO) {

        JobResponse jobResponse = new JobResponse();
        UserDTO userDTO = getUser(jobDTO.getUserId());
        if(userDTO.getUsername() != null) {
            System.out.println(userDTO.getUsername());
            JobModel jobModel = new JobModel();
            jobModel = jobMapper.convertToModel(jobDTO);

            jobRepository.save(jobModel);
            log.info("Job Data Saved To DB.");
            jobResponse = jobMapper.convertToResponse(jobDTO);
            jobResponse.setJobId(jobModel.getJobId());
            jobResponse.setJobCreatedOn(LocalDate.now().toString());
            jobResponse.setMessage("Job Created Successfully");

            return jobResponse;
        }
        jobResponse.setErrorResponse(new ErrorResponse("Invalid User ID: "+jobDTO.getUserId()+"\n User Data Not Found"));
        return jobResponse;
    }

    public UserDTO getUser(Long userId) {
        return userFeign.getUser(userId).getBody();
    }


    @KafkaListener(groupId = "interviewdate-group", topics = "job-tracker")
    public ResponseEntity<InterviewNotificationResponse> checkforInterviewUpdate(InterviewNotificationResponse interviewNotificationResponse) {

        if(interviewNotificationResponse.getMessage() != null) {
            JobDTO jobDTO = interviewNotificationResponse.getJobDTO();
            Optional<JobModel> optionalJobModel =
                    jobRepository.findById(jobDTO.getJobId());
            JobModel jobModel = optionalJobModel.orElse(new JobModel());
            jobModel = jobMapper.convertToModel(jobDTO);

            jobRepository.save(jobModel);
            log.info("job details updated....");
        }
        System.out.println(interviewNotificationResponse);
        return ResponseEntity.ok().body(interviewNotificationResponse);
    }

    public JobDTO getJobWithID(Long jobId) {
        return jobRepository.findById(jobId)
                .map(data -> new JobDTO(data.getJobId(),
                        data.getTitle(), data.getCompany(),
                        data.getStatus(),data.getAppliedDate(),
                        data.getFollowUpDate(),data.getInterviewDate(),
                        data.getUserId())).orElse(new JobDTO());
    }

    public List<JobDTO> getJobs(Long userId) {
        return jobRepository.findJobByUserId(userId)
                .stream().map(data -> new JobDTO(data.getJobId(),
                        data.getTitle(), data.getCompany(),
                        data.getStatus(),data.getAppliedDate(),
                        data.getFollowUpDate(),data.getInterviewDate(),
                        data.getUserId())).toList();
    }

    public String removeJobViaID(Long jobId) {
        jobRepository.deleteById(jobId);
        log.info("job with id: {} deleted",jobId);
        return "job with id: "+jobId+" deleted";
    }

    public JobDTO updateJob(Long jobId, JobDTO newJobDTO) {
        JobModel data = jobRepository.findById(jobId).orElse(new JobModel());
        data.setStatus(newJobDTO.getStatus());
        data.setCompany(newJobDTO.getCompany());
        data.setTitle(newJobDTO.getTitle());
        data.setAppliedDate(newJobDTO.getAppliedDate());
        data.setFollowUpDate(newJobDTO.getFollowUpDate());
        data.setInterviewDate(newJobDTO.getInterviewDate());

        jobRepository.save(data);
        return newJobDTO;
    }
}
