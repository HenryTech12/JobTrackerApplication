package org.jobtracker.app.NotificationService.feign;

import org.jobtracker.app.NotificationService.dto.JobDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("JOBSERVICE")
public interface AppFeign {

    @GetMapping("/job/api/myjob/id/{jobId}")
    ResponseEntity<JobDTO> getJobById(@PathVariable Long jobId);
}
