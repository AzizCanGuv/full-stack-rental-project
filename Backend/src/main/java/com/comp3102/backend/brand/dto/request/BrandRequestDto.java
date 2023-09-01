package com.comp3102.backend.brand.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequestDto {
    @Size(max = 32, min = 3, message = "Brand name should be smaller than 32 characters or longer than 3 characters")
    private String brandName;

}
