package com.comp3102.backend.user.dto.converter;


import com.comp3102.backend.user.dto.response.UserResponseDto;
import com.comp3102.backend.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class User2UserResponseDtoConverter {

    public UserResponseDto convert(User user) {
        return new UserResponseDto(user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getDriverLicense(),
                user.getRole(),
                user.getBirthDate(),
                user.isEnabled()
        );
    }

    public List<UserResponseDto> convert(List<User> users) {
        return users.stream().map(user -> new UserResponseDto(user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getDriverLicense(),
                user.getRole(),
                user.getBirthDate(),
                user.isEnabled())).collect(Collectors.toList());
    }

}