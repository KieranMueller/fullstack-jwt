package com.kieran.jwt.service;

import com.kieran.jwt.dto.CredentialsDto;
import com.kieran.jwt.dto.RegisterDto;
import com.kieran.jwt.dto.UserDto;

public interface UserService {
    UserDto login(CredentialsDto credentialsDto);

    UserDto register(RegisterDto registerDto);
}
