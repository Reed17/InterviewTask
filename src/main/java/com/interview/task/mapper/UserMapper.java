package com.interview.task.mapper;

import com.interview.task.dto.UserDto;
import com.interview.task.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto) {
        final User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        if (userDto.getRoles() == null) {
            user.setRoles(new HashSet<>());
        } else {
            user.setRoles(userDto.getRoles());
        }
        if (userDto.getWallets() == null) {
            user.setWallets(new HashSet<>());
        } else {
            user.setWallets(userDto.getWallets());
        }
        return user;
    }

    public UserDto toDto(User user) {
        final UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        if (user.getRoles() == null) {
            userDto.setRoles(new HashSet<>());
        } else {
            userDto.setRoles(user.getRoles());
        }
        if (user.getWallets() == null) {
            userDto.setWallets(new HashSet<>());
        } else {
            userDto.setWallets(user.getWallets());
        }
        return userDto;
    }
}
