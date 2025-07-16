package org.jobtracker.app.NotificationService.service;

import org.jobtracker.app.NotificationService.response.InterviewNotificationResponse;
import org.jobtracker.app.NotificationService.dto.JobDTO;
import org.jobtracker.app.NotificationService.feign.AppFeign;
import org.jobtracker.app.NotificationService.request.InterviewNotificationRequest;
import org.jobtracker.app.NotificationService.status.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private AppFeign appFeign;

    @Autowired
    private KafkaTemplate<String, InterviewNotificationResponse> kafkaTemplate;

    public InterviewNotificationResponse checkForInterview(InterviewNotificationRequest interviewNotificationRequest) {
        JobDTO jobDTO = appFeign.getJobById(interviewNotificationRequest.getJobId()).getBody();
        if(!Objects.isNull(jobDTO)) {
           InterviewNotificationResponse interviewNotificationResponse = getInterviewMessage(jobDTO);
           kafkaTemplate.send("job-tracker", interviewNotificationResponse);
           return interviewNotificationResponse;
        }
        return new InterviewNotificationResponse();
    }

    public InterviewNotificationResponse getInterviewMessage(JobDTO jobDTO) {
        LocalDate localDate = LocalDate.now();
        LocalDate interviewDate = LocalDate.parse(jobDTO.getInterviewDate());
        InterviewNotificationResponse interviewNotificationResponse = new InterviewNotificationResponse();
        interviewNotificationResponse.setJobDTO(jobDTO);
        if(localDate.isBefore(interviewDate)) {
            jobDTO.setStatus(JobStatus.NOT_INTERVIEWED);
            interviewNotificationResponse.setMessage("You have an interview" +
                    " on the : "+jobDTO.getInterviewDate()+" for the below job details: "+jobDTO);
        }
        else{
            if(localDate.isEqual(interviewDate)) {
                jobDTO.setStatus(JobStatus.INTERVIEW_ONGOING);
                interviewNotificationResponse.setMessage("Your Scheduled Date For The Interview Has  Make Sure You Take The Interview" +
                        "\n for the below job details: "+jobDTO);
            }
            jobDTO.setStatus(JobStatus.INTERVIEWED);
            interviewNotificationResponse.setMessage("The Interview Has Already Ended, Did you take the interview, If you didnt make sure to reschedule interview Date" +
                    " for the below job details: "+jobDTO);
        }
        System.out.println("hello");
        return interviewNotificationResponse;
    }
}
