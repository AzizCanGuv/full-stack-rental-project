package com.comp3102.backend.car.dto.converter;

import com.comp3102.backend.car.dto.response.CarGetDetailsDto;
import com.comp3102.backend.car.Car;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Car2CarGetDetailsDtoConverter {


    public CarGetDetailsDto convert(Car from) {
        return new CarGetDetailsDto(from.getCarId(),
                from.getBrand().getBrandName(),
                from.getColor().getColorName(),
                from.getDescription(),
                from.getDailyRentPrice(),
                from.getEnginePower(),
                from.getYear(),
                from.getLocation(),
                from.getCreatedAt(),
                from.getTransmissionType(),
                from.getIsBooked(),
                from.getCarImageId());
    }

    public List<CarGetDetailsDto> convert(List<Car> from) {
        return from.stream().map(c -> new CarGetDetailsDto(
                        c.getCarId(),
                        c.getBrand().getBrandName(),
                        c.getColor().getColorName(),
                        c.getDescription(),
                        c.getDailyRentPrice(),
                        c.getEnginePower(),
                        c.getYear(),
                        c.getLocation(),
                        c.getCreatedAt(),
                        c.getTransmissionType(),
                        c.getIsBooked(),
                        c.getCarImageId()))
                .collect(Collectors.toList());
    }
}
