package com.kieran.jwt.mapper;

import com.kieran.jwt.dto.SignUpDto;
import com.kieran.jwt.dto.UserDto;
import com.kieran.jwt.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
