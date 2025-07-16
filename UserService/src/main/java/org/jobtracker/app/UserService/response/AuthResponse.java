package org.jobtracker.app.UserService.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String username;
    private String email;
    private String jwtToken;
    private String refreshToken;
}
