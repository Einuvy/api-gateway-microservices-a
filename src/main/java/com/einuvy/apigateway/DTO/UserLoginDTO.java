package com.einuvy.apigateway.DTO;

import com.einuvy.apigateway.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for {@link User}
 */
@AllArgsConstructor
@Getter
public class UserLoginDTO {
    private final String username;
    private final String password;

}
