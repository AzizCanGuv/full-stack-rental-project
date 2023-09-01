package com.comp3102.backend.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTicketRequestDto {
    @NotBlank(message = "First Name field is mandatory")
    @Size(min = 2, message = "First Name must be at least 2 characters")
    private String firstName;
    @NotBlank(message = "Last Name field is mandatory")
    @Size(min = 2, message = "Last Name must be at least 2 characters")
    private String lastName;
    @NotBlank(message = "Email field is mandatory")
    @Email(message = "Your email entry does not match with email pattern")
    private String email;
    @NotBlank(message = "Content field is mandatory")
    @Size(min = 2, message = "Last Name must be at least 2 characters")
    private String content;
}
