package com.comp3102.backend.brand;

import com.comp3102.backend.core.exceptions.EntityAlreadyExistsException;
import com.comp3102.backend.core.exceptions.EntityNotFoundException;
import com.comp3102.backend.brand.dto.request.BrandRequestDto;
import com.comp3102.backend.brand.dto.response.BrandResponseDto;
import com.comp3102.backend.brand.dto.converter.Brand2BrandResponseDtoConverter;
import com.comp3102.backend.brand.dto.converter.BrandRequestDto2BrandConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.comp3102.backend.core.constant.ErrorConstant.*;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final Brand2BrandResponseDtoConverter brand2BrandResponseDtoConverter;
    private final BrandRequestDto2BrandConverter brandRequestDto2BrandConverter;
    public List<BrandResponseDto> getAll(){
        return brand2BrandResponseDtoConverter.convert(brandRepository.findAll());
    }
    public BrandResponseDto addNewBrand(BrandRequestDto brand){
        brandRepository.findByBrandName(brand.getBrandName()).ifPresent(s -> {throw new EntityAlreadyExistsException(errorMessageParser(BRAND_ALREADY_EXISTS_MESSAGE, brand.getBrandName()));});
        Brand brandInDb = brandRepository.save(brandRequestDto2BrandConverter.convert(brand));
        return brand2BrandResponseDtoConverter.convert(brandInDb);
    }

    public BrandResponseDto getBrandById(Integer id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(errorMessageParser(BRAND_NOT_FOUND_MESSAGE, id)));
        return brand2BrandResponseDtoConverter.convert(brand);
    }

    public void deleteBrand(Integer id) {
        brandRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(errorMessageParser(BRAND_NOT_FOUND_MESSAGE, id)));
        brandRepository.deleteById(id);
    }
    public BrandResponseDto getBrandByName(String brandName){
        Brand brand = brandRepository.findByBrandName(brandName).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(BRAND_NOT_FOUND_MESSAGE, brandName)));
        return brand2BrandResponseDtoConverter.convert(brand);
    }

    public BrandResponseDto updateBrand(Integer id, BrandRequestDto brand) {
        brandRepository.findByBrandName(brand.getBrandName()).ifPresent(s -> {throw new EntityAlreadyExistsException("Entity already exists");});
        if (!brandRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("Entity Not Found!!");
        }
        Brand brandToUpdate = brandRepository.save(brandRequestDto2BrandConverter.convert(id, brand));
        return brand2BrandResponseDtoConverter.convert(brandToUpdate);
    }


}