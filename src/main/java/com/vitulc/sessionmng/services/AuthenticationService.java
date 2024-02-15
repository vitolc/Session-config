package com.vitulc.sessionmng.services;

import com.vitulc.sessionmng.dtos.LoginRequestDto;
import com.vitulc.sessionmng.dtos.UserDto;
import com.vitulc.sessionmng.entities.Users;
import com.vitulc.sessionmng.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public AuthenticationService(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository) {

        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> register(UserDto userDto) {
        var encryptedPassword = passwordEncoder.encode(userDto.password());
        var user = new Users(userDto, encryptedPassword);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Users registered successfully");
    }

    public ResponseEntity<String> login(LoginRequestDto loginRequestDto) {

         /*
            Create an unauthenticated Authentication request using the username and password
            from the loginRequestDto object. It is unauthenticated because it is not yet verified.
         */
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginRequestDto.username(), loginRequestDto.password());

         /*
           Invoking this method will authenticate the authentication request object.
           If successfully authenticated, it will return an authenticated Authentication object.
         */
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

         /*
            Create a new empty security context. The idea is to put an authenticated authentication
            into a security context, and then set that security context in the SecurityContextHolder.
         */
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationResponse);
        SecurityContextHolder.setContext(securityContext);

        return ResponseEntity.ok().body("Login successfully");
    }
}
