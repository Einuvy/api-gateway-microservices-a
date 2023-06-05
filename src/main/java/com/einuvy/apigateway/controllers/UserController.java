package com.einuvy.apigateway.controllers;

import com.einuvy.apigateway.configuration.UserPrincipal;
import com.einuvy.apigateway.enums.Role;
import com.einuvy.apigateway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PatchMapping("/change/{role}")
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Role role){
        userService.changeRole(role, principal.getUsername());

        return new ResponseEntity<>("Role updated",OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(userService.authUser(userPrincipal.getUsername()), OK);
    }
}
