package com.comp3102.backend.color.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColorRequestDto {
    @Size(max = 10, min = 3, message = "Color name should be smaller than 10 characters or longer than 3 characters")
    private String colorName;
}
