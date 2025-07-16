package org.jobtracker.app.JobTracker.controller;

import org.jobtracker.app.JobTracker.dto.JobDTO;
import org.jobtracker.app.JobTracker.response.JobResponse;
import org.jobtracker.app.JobTracker.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/job/api")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/create")
    public ResponseEntity<JobResponse> createJob(@RequestBody JobDTO jobDTO) {
        JobResponse jobResponse = null;
         if(!Objects.isNull(jobDTO)) {
            jobResponse = jobService.createJob(jobDTO);
         }
         return new ResponseEntity<>(jobResponse, HttpStatus.OK);
    }

    @GetMapping("/myjob/id/{jobId}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long jobId) {
        return new ResponseEntity<>(jobService.getJobWithID(jobId),HttpStatus.OK);
    }

    @GetMapping("/jobs/user/{userId}")
    public ResponseEntity<List<JobDTO>> getJobs(@PathVariable Long userId) {
        return new ResponseEntity<>(jobService.getJobs(userId), HttpStatus.OK);
    }

    @PutMapping("/update/id/{jobId}")
    public ResponseEntity<JobDTO> updateJobData(Long jobId, @RequestBody JobDTO jobDTO) {
        return new ResponseEntity<>(jobService.updateJob(jobId,jobDTO),HttpStatus.OK);
    }

    @DeleteMapping("/delete/id/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId) {
        return new ResponseEntity<>(jobService.removeJobViaID(jobId),HttpStatus.OK);
    }
}
