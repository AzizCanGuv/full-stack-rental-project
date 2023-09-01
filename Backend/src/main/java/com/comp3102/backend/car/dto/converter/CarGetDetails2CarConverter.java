package com.comp3102.backend.car.dto.converter;


import com.comp3102.backend.brand.BrandService;
import com.comp3102.backend.brand.dto.converter.BrandResponseDto2BrandConverter;
import com.comp3102.backend.car.Car;
import com.comp3102.backend.car.dto.response.CarGetDetailsDto;
import com.comp3102.backend.color.ColorService;
import com.comp3102.backend.color.dto.converter.ColorResponseDto2ColorConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarGetDetails2CarConverter {

    private final ColorService colorService;
    private final BrandService brandService;
    private final ColorResponseDto2ColorConverter colorResponseDto2ColorConverter;
    private final BrandResponseDto2BrandConverter brandResponseDto2BrandConverter;
    public Car convert(CarGetDetailsDto from) {
        return new Car(from.getCarId(),
                from.getDescription(),
                from.getDailyRentPrice(),
                from.getEnginePower(),
                from.getYear(),
                from.getLocation(),
                from.getCreatedAt(),
                from.getTransmissionType(),
                brandResponseDto2BrandConverter.convert(brandService.getBrandByName(from.getBrandName())),
                colorResponseDto2ColorConverter.convert(colorService.getColorByName(from.getColorName())));
    }

}
