package com.comp3102.backend.car.dto.converter;

import com.comp3102.backend.brand.dto.converter.BrandResponseDto2BrandConverter;
import com.comp3102.backend.car.dto.request.CarPostDetailsDto;
import com.comp3102.backend.car.Car;
import com.comp3102.backend.brand.BrandService;
import com.comp3102.backend.color.ColorService;
import com.comp3102.backend.color.dto.converter.ColorResponseDto2ColorConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CarPostDetailsDto2CarConverter {
    private final ColorService colorService;
    private final BrandService brandService;
    private final BrandResponseDto2BrandConverter brandResponseDto2BrandConverter;
    private final ColorResponseDto2ColorConverter colorResponseDto2ColorConverter;
    public Car convert(CarPostDetailsDto from){
        return new Car(null,
                from.getDescription(),
                from.getDailyRentPrice(),
                from.getEnginePower(),
                from.getYear(),
                from.getLocation(),
                new Date(), false,
                from.getTransmissionType(),
                brandResponseDto2BrandConverter.convert(brandService.getBrandByName(from.getBrandName())),
                colorResponseDto2ColorConverter.convert(colorService.getColorByName(from.getColorName())),
                null
        );
    }

    public Car convert(Integer id, CarPostDetailsDto from){
        return new Car(id,
                from.getDescription(),
                from.getDailyRentPrice(),
                from.getEnginePower(),
                from.getYear(),
                from.getLocation(),
                new Date(),
                from.getTransmissionType(),
                brandResponseDto2BrandConverter.convert(brandService.getBrandByName(from.getBrandName())),
                colorResponseDto2ColorConverter.convert(colorService.getColorByName(from.getColorName()))
        );
    }
}
