package com.comp3102.backend.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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
public class RegisterRequestDto {

    @NotBlank(message = "First Name cannot be empty")
    private String firstName;
    @NotBlank(message = "Last Name cannot be empty")
    private String lastName;
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @NotBlank(message = "Identity Number  cannot be empty")
    private String identityNumber;
    @Past(message = "Birth Date have to be past from this time")
    private LocalDateTime birthDate;
    @NotBlank(message = "Phone Number cannot be empty")
    private String phoneNumber;
    @NotBlank(message = "Driver License cannot be empty")
    private String driverLicense;

}