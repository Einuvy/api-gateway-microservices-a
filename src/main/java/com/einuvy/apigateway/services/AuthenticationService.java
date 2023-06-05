package com.einuvy.apigateway.services;

import com.einuvy.apigateway.DTO.UserAuthDTO;
import com.einuvy.apigateway.DTO.UserLoginDTO;
import com.einuvy.apigateway.models.User;

public interface AuthenticationService {
    UserAuthDTO singInAndReturnJWT(UserLoginDTO userLoginDTO);
}
