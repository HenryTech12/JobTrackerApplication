package org.jobtracker.app.UserService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    private Long userId;
    private String email;
    private String message;
    private boolean isRegistered;
}
