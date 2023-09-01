package com.comp3102.backend.brand.dto.converter;

import com.comp3102.backend.brand.dto.response.BrandResponseDto;
import com.comp3102.backend.brand.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandResponseDto2BrandConverter {
   public Brand convert(BrandResponseDto from) {
        return new Brand(from.getId(), from.getBrandName());
    }
}
