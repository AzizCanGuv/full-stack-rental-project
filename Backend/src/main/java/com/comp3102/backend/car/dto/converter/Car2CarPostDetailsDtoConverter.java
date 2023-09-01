package com.comp3102.backend.car.dto.converter;

import com.comp3102.backend.car.dto.request.CarPostDetailsDto;
import com.comp3102.backend.car.Car;
import org.springframework.stereotype.Component;

@Component
public class Car2CarPostDetailsDtoConverter {

    public CarPostDetailsDto convert(Car from) {
        return new CarPostDetailsDto(from.getBrand().getBrandName(),
                from.getColor().getColorName(),
                from.getDescription(),
                from.getDailyRentPrice(),
                from.getEnginePower(),
                from.getYear(),
                from.getLocation(),
                from.getTransmissionType());
    }

}
