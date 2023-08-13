package com.kieran.jwt.service.impl;

import com.kieran.jwt.config.AuthProvider;
import com.kieran.jwt.dto.CredentialsDto;
import com.kieran.jwt.dto.RegisterDto;
import com.kieran.jwt.dto.UserDto;
import com.kieran.jwt.entity.User;
import com.kieran.jwt.exception.AppException;
import com.kieran.jwt.mapper.UserMapper;
import com.kieran.jwt.repository.UserRepository;
import com.kieran.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final AuthProvider auth;

    @Override
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new AppException("user not found", HttpStatus.NOT_FOUND));
        if (encoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            UserDto dto = userMapper.toUserDto(user);
            dto.setToken(auth.createToken(dto));
            return dto;
        }
        throw new AppException("invalid credentials", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDto register(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent())
            throw new AppException("user already exists", HttpStatus.BAD_REQUEST);
        User newUser = userMapper.registerDtoToUser(registerDto);
        newUser.setPassword(encoder.encode(registerDto.getPassword()));
        UserDto dto = userMapper.toUserDto(userRepository.save(newUser));
        dto.setToken(auth.createToken(dto));
        return dto;
    }
}
