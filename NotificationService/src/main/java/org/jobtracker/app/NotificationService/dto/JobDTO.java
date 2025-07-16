package org.jobtracker.app.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jobtracker.app.NotificationService.status.JobStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {

    private Long jobId;
    private String title;
    private String company;
    private JobStatus status;
    private String appliedDate;
    private String followUpDate;
    private String interviewDate;
    private Long userId;
}
