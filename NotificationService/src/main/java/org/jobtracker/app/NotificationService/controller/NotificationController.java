package org.jobtracker.app.NotificationService.controller;

import org.jobtracker.app.NotificationService.response.InterviewNotificationResponse;
import org.jobtracker.app.NotificationService.request.InterviewNotificationRequest;
import org.jobtracker.app.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/interview")
    public ResponseEntity<InterviewNotificationResponse> checkforInterview(@RequestBody InterviewNotificationRequest interviewNotificationRequest) {
        return ResponseEntity.ok().body(notificationService.checkForInterview(interviewNotificationRequest));
    }
}
