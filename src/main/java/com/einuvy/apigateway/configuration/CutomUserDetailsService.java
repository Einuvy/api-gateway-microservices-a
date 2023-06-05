package com.einuvy.apigateway.configuration;

import com.einuvy.apigateway.models.User;
import com.einuvy.apigateway.services.UserService;
import com.einuvy.apigateway.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CutomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user " + username));
        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name()));
        return UserPrincipal
                .builder()
                .user(user)
                .id(user.getId())
                .username(username)
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
