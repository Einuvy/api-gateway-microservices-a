package com.einuvy.apigateway.controllers;

import com.einuvy.apigateway.DTO.UserCreationDTO;
import com.einuvy.apigateway.DTO.UserLoginDTO;
import com.einuvy.apigateway.models.User;
import com.einuvy.apigateway.services.AuthenticationService;
import com.einuvy.apigateway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<?> singUp(@RequestBody UserCreationDTO userCreationDTO){
        if (userService.existByUsername(userCreationDTO.getUsername())){
            return new ResponseEntity<>("This username is already registered", CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(userCreationDTO), CREATED);

    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> singIn(@RequestBody UserLoginDTO userLoginDTO){
        return new ResponseEntity<>(authenticationService.singInAndReturnJWT(userLoginDTO), OK);
    }


}
