package com.comp3102.backend.car.dto.response;

import com.comp3102.backend.car.Transmission;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
public class CarGetDetailsDto {

    private Integer carId;
    private String brandName;
    private String colorName;
    private String description;
    private Double dailyRentPrice;
    private String enginePower;
    private String year;
    private String location;
    private Date createdAt;
    private Transmission transmissionType;
    private Boolean isBooked;
    private String carImageId;
    public CarGetDetailsDto(Integer carId, String brandName, String colorName, String description, Double dailyRentPrice, String enginePower, String year, String location, Date createdAt, Transmission transmissionType, Boolean isBooked, String carImageId) {
        this.carId = carId;
        this.brandName = brandName;
        this.colorName = colorName;
        this.description = description;
        this.dailyRentPrice = dailyRentPrice;
        this.enginePower = enginePower;
        this.year = year;
        this.location = location;
        this.createdAt = createdAt;
        this.transmissionType = transmissionType;
        this.isBooked = isBooked;
        this.carImageId = carImageId;
    }

}
