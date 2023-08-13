package com.kieran.jwt.service.impl;

import com.kieran.jwt.config.UserAuthProvider;
import com.kieran.jwt.dto.CredentialsDto;
import com.kieran.jwt.dto.SignUpDto;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserAuthProvider auth;

    @Override
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("user not found", HttpStatus.NOT_FOUND));
        if (encoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            UserDto dto = userMapper.toUserDto(user);
            dto.setToken(auth.createToken(dto));
            return dto;
        }
        throw new AppException("invalid credentials", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDto register(SignUpDto signUpDto) {
        if(userRepository.findByLogin(signUpDto.getLogin()).isPresent())
            throw new AppException("user already exists", HttpStatus.BAD_REQUEST);
        User newUser = userMapper.signUpToUser(signUpDto);
        newUser.setPassword(encoder.encode(signUpDto.getPassword()));
        UserDto dto = userMapper.toUserDto(userRepository.save(newUser));
        dto.setToken(auth.createToken(dto));
        return dto;
    }
}
