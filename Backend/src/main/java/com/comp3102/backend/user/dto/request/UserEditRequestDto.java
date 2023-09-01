package com.comp3102.backend.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEditRequestDto {

    private String newPassword;
    private String phoneNumber;
    private String driverLicense;

}
