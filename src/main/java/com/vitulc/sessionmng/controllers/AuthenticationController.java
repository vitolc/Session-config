package com.vitulc.sessionmng.controllers;

import com.vitulc.sessionmng.dtos.LoginRequestDto;
import com.vitulc.sessionmng.dtos.UserDto;
import com.vitulc.sessionmng.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDto userDto) {
        return authenticationService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return authenticationService.login(loginRequestDto);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        var loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(String.format("Hello %s", loggedUserName));
    }
}
