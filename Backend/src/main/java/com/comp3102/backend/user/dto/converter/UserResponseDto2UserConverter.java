package com.comp3102.backend.user.dto.converter;

import com.comp3102.backend.user.User;
import com.comp3102.backend.user.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;


@Component
public class UserResponseDto2UserConverter {

    public User convert(UserResponseDto from){
        return new User(from.getUserId(),
                from.getFirstName(),
                from.getLastName(),
                from.getEmail(),
                from.getBirthDate(),
                from.getDriverLicense(),
                from.getRole()
        );
    }

}
