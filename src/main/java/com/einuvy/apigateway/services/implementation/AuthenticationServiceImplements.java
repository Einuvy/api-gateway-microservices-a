package com.einuvy.apigateway.services.implementation;

import com.einuvy.apigateway.DTO.UserAuthDTO;
import com.einuvy.apigateway.DTO.UserLoginDTO;
import com.einuvy.apigateway.configuration.UserPrincipal;
import com.einuvy.apigateway.configuration.jwt.JwtProvider;
import com.einuvy.apigateway.models.User;
import com.einuvy.apigateway.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImplements implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserAuthDTO singInAndReturnJWT(UserLoginDTO userLoginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        User singInUser = userPrincipal.getUser();
        UserAuthDTO userAuth = new UserAuthDTO(singInUser);
        userAuth.setToken(jwt);

        return userAuth;

    }

}
