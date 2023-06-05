package com.einuvy.apigateway.configuration.jwt;

import com.einuvy.apigateway.configuration.UserPrincipal;
import com.einuvy.apigateway.models.User;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtProvider {
    String generateToken(UserPrincipal auth);

    String generateToken(User user);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);
}
