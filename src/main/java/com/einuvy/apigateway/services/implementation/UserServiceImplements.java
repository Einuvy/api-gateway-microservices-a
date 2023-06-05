package com.einuvy.apigateway.services.implementation;

import com.einuvy.apigateway.DTO.UserAuthDTO;
import com.einuvy.apigateway.DTO.UserCreationDTO;
import com.einuvy.apigateway.configuration.jwt.JwtProvider;
import com.einuvy.apigateway.enums.Role;
import com.einuvy.apigateway.models.User;
import com.einuvy.apigateway.DTO.UserDTO;
import com.einuvy.apigateway.repositories.UserRepository;
import com.einuvy.apigateway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static com.einuvy.apigateway.enums.Role.USER;

@Service
public class UserServiceImplements implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserAuthDTO saveUser(UserCreationDTO userCreationDTO){



        User user = new User(userCreationDTO.getUsername(),
                passwordEncoder.encode(userCreationDTO.getPassword()),
                userCreationDTO.getName(),
                userCreationDTO.getLastName(),
                USER);

        User createdUser = userRepository.save(user);
        String jwt = jwtProvider.generateToken(createdUser);

        UserAuthDTO userAuthenticated = new UserAuthDTO(createdUser);
        userAuthenticated.setToken(jwt);


        return userAuthenticated;
    }

    @Override
    public UserDTO findByUsername(String username){
        return new UserDTO(userRepository.findByUsername(username).orElse(new User()));
    }

    @Override
    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Transactional
    @Override
    public void changeRole(Role role, String username){
        userRepository.updateUserRole(username, role);
    }

    @Override
    public UserAuthDTO authUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Unknown user " + username));

        String jwt = jwtProvider.generateToken(user);
        UserAuthDTO userAuthDTO= new UserAuthDTO(user);
        userAuthDTO.setToken(jwt);

        return  userAuthDTO;
    }
}
