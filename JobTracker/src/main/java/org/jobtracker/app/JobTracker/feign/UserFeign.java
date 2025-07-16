package org.jobtracker.app.JobTracker.feign;

import org.jobtracker.app.JobTracker.configuration.UserFeignConfiguration;
import org.jobtracker.app.JobTracker.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERSERVICE", configuration = UserFeignConfiguration.class)
public interface UserFeign {

    @GetMapping("/user/api/get/id/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId);
}
