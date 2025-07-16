package org.jobtracker.app.NotificationService.request;

import lombok.Data;

@Data

public class InterviewNotificationRequest {

    private Long jobId;
    private String message;

}
