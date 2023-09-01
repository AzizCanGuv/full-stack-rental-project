package com.comp3102.backend.user.dto.response;

import com.comp3102.backend.user.Role;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("user")
public class AuthenticationResponseDto {
    private Integer id;
    private String name;
    private String lastName;
    private String token;
    private Role role;
    private String phoneNumber;
    private String driverLicense;
    private String email;

}