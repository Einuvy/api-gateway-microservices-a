package com.einuvy.apigateway.DTO;

import com.einuvy.apigateway.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Getter
public class UserCreationDTO implements Serializable {
    private String username;
    private String password;
    private String name;
    private String lastName;

}