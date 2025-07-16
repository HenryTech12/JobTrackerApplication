package org.jobtracker.app.NotificationService.response;

import lombok.Data;
import org.jobtracker.app.JobTracker.dto.JobDTO;

@Data
public class InterviewNotificationResponse {

    private JobDTO jobDTO;
    private String message;
}
