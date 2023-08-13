package com.kieran.jwt.service;

import com.kieran.jwt.dto.CredentialsDto;
import com.kieran.jwt.dto.SignUpDto;
import com.kieran.jwt.dto.UserDto;

public interface UserService {
    UserDto login(CredentialsDto credentialsDto);

    UserDto register(SignUpDto signUpDto);
}
