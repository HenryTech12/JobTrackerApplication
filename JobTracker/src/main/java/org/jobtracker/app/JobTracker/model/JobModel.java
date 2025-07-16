package org.jobtracker.app.JobTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jobtracker.app.JobTracker.dto.UserDTO;
import org.jobtracker.app.JobTracker.status.JobStatus;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobModel {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long jobId;
    private String title;

    @Column(unique = true)
    private String company;
    private JobStatus status;
    private String appliedDate;
    private String followUpDate;
    private String interviewDate;
    private Long userId;
    private UserDTO userDTO;
}
