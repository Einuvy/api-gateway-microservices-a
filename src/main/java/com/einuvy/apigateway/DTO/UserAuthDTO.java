package com.einuvy.apigateway.DTO;

import com.einuvy.apigateway.enums.Role;
import com.einuvy.apigateway.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link User}
 */


@Getter
public class UserAuthDTO  implements Serializable {
    private final Long id;
    private final String username;
    private final String name;
    private final String lastName;

    @JsonFormat(pattern = "dd/MM/yy - HH:mm:ss")
    private final LocalDateTime creationDate;
    private final Role role;

    @Setter
    private String token;


    public UserAuthDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getLastName();
        this.lastName = user.getLastName();
        this.creationDate = user.getCreationDate();
        this.role = user.getRole();
    }
}