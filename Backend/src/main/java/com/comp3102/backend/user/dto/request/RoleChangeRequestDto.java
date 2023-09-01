package com.comp3102.backend.user.dto.request;

import com.comp3102.backend.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleChangeRequestDto {
    private String userEmail;
    private Role role;
}
