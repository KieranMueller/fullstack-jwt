package com.kieran.jwt.controller;

import com.kieran.jwt.dto.CredentialsDto;
import com.kieran.jwt.dto.RegisterDto;
import com.kieran.jwt.dto.UserDto;
import com.kieran.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public UserDto login(@RequestBody CredentialsDto credentialsDto) {
        return userService.login(credentialsDto);
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }
}
