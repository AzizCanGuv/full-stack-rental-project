package com.comp3102.backend.brand.dto.converter;

import com.comp3102.backend.brand.dto.request.BrandRequestDto;
import com.comp3102.backend.brand.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandRequestDto2BrandConverter {

    public Brand convert(BrandRequestDto from) {
        return new Brand(null, from.getBrandName());
    }
    public Brand convert(Integer id, BrandRequestDto from) {
        return new Brand(id, from.getBrandName());
    }
}
