package org.jobtracker.app.JobTracker.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jobtracker.app.JobTracker.status.JobStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {

    private Long jobId;
    private String title;
    private String company;
    private JobStatus status;
    private String jobCreatedOn;
    private String interviewDate;
    private String message;
    private ErrorResponse errorResponse;
}
