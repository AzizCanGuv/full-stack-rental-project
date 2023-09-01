package com.comp3102.backend.brand.dto.converter;

import com.comp3102.backend.brand.dto.response.BrandResponseDto;
import com.comp3102.backend.brand.Brand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Brand2BrandResponseDtoConverter {

    public BrandResponseDto convert(Brand from) {
        return new BrandResponseDto(from.getBrandId(), from.getBrandName());
    }
    public List<BrandResponseDto> convert(List<Brand> from) {
        return from.stream().map(b -> new BrandResponseDto(b.getBrandId(), b.getBrandName())).collect(Collectors.toList());
    }
}
