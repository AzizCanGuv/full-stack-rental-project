package com.comp3102.backend.user.dto.response;

import com.comp3102.backend.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private String driverLicense;
    private Role role;
    private LocalDateTime birthDate;
    private Boolean isEnabled;
}
