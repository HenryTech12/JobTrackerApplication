package org.jobtracker.app.JobTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jobtracker.app.JobTracker.status.JobStatus;

import java.time.LocalDate;

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
