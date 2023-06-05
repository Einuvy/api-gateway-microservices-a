package com.einuvy.apigateway.services;

import com.einuvy.apigateway.DTO.UserAuthDTO;
import com.einuvy.apigateway.DTO.UserCreationDTO;
import com.einuvy.apigateway.enums.Role;
import com.einuvy.apigateway.DTO.UserDTO;
import com.einuvy.apigateway.models.User;

import java.util.Optional;

public interface UserService {

    UserAuthDTO saveUser(UserCreationDTO userCreationDTO);

    UserDTO findByUsername(String username);

    Optional<User> findUserByUsername(String username);

    Boolean existByUsername(String username);

    void changeRole(Role role, String username);

    UserAuthDTO authUser(String username);
}
