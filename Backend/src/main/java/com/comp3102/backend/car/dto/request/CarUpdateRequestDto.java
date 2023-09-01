package com.comp3102.backend.car.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarUpdateRequestDto {
    @NotBlank(message = "Location Cannot be null or blank")
    private String location;
    private String description;
    @NotNull(message = "Daily Rent Price Cannot be null or blank")
    private Double dailyRentPrice;
}
