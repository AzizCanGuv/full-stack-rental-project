package com.comp3102.backend.car.dto.request;

import com.comp3102.backend.car.Transmission;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("car")
public class CarPostDetailsDto {
    @NotBlank(message = "Brand Name Cannot be null or blank")
    private String brandName;
    @NotBlank(message = "Color Name Cannot be null or blank")
    private String colorName;
    private String description;
    @NotNull(message = "Daily Rent Price Cannot be null or blank")
    private Double dailyRentPrice;
    @NotBlank(message = "Engine Power Cannot be null or blank")
    private String enginePower;
    @NotBlank(message = "Year Cannot be null or blank")
    @Size(min = 4, max = 4, message = "Year String can be maximum 4 and minimum 4 length")
    private String year;
    @NotBlank(message = "Location Cannot be null or blank")
    private String location;
    @NotNull(message = "Transmission Type Cannot be null or blank")
    private Transmission transmissionType;
}
