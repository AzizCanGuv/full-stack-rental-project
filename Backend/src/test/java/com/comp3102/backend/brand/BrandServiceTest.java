package com.comp3102.backend.brand;

import com.comp3102.backend.brand.dto.converter.Brand2BrandResponseDtoConverter;
import com.comp3102.backend.brand.dto.converter.BrandRequestDto2BrandConverter;
import com.comp3102.backend.brand.dto.converter.BrandResponseDto2BrandConverter;
import com.comp3102.backend.brand.dto.request.BrandRequestDto;
import com.comp3102.backend.brand.dto.response.BrandResponseDto;
import com.comp3102.backend.core.exceptions.EntityAlreadyExistsException;
import com.comp3102.backend.core.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BrandServiceTest {

    private BrandService brandService;
    private BrandRepository brandRepository;
    private Brand2BrandResponseDtoConverter brand2BrandResponseDtoConverter;
    private BrandRequestDto2BrandConverter brandRequestDto2BrandConverter;

    private BrandResponseDto2BrandConverter brandResponseDto2BrandConverter;

    @BeforeEach
    public void setUp() {
        brandRepository = mock(BrandRepository.class);
        brand2BrandResponseDtoConverter = mock(Brand2BrandResponseDtoConverter.class);
        brandRequestDto2BrandConverter = mock(BrandRequestDto2BrandConverter.class);
        brandResponseDto2BrandConverter = mock(BrandResponseDto2BrandConverter.class);
        brandService = new BrandService(brandRepository, brand2BrandResponseDtoConverter, brandRequestDto2BrandConverter);

    }

    private Brand createBrand(int id, String name) {
        return new Brand(id, name);
    }

    private BrandResponseDto createBrandResponseDto(int id, String name) {
        return new BrandResponseDto(id, name);
    }

    private BrandRequestDto createBrandRequestDto(String name) {
        return new BrandRequestDto(name);
    }

    @Test
    @DisplayName("Get All Brands")
    public void testGetAllBrand_whenBrandExistsInDB_shouldReturnBrands() {
        List<Brand> brands = Arrays.asList(
                createBrand(0, "Brand 1"),
                createBrand(1, "Brand 2")
        );

        List<BrandResponseDto> brandResponseDtos = Arrays.asList(
                createBrandResponseDto(0, "Brand 1"),
                createBrandResponseDto(1, "Brand 2")
        );

        when(brandRepository.findAll()).thenReturn(brands);
        when(brand2BrandResponseDtoConverter.convert(brands)).thenReturn(brandResponseDtos);

        List<BrandResponseDto> result = brandService.getAll();

        assertEquals(brandResponseDtos, result);
    }

    @Test
    @DisplayName("Add New Brand Should Save")
    public void testAddNewBrand_whenBrandNameDoesNotExist_shouldSaveNewBrand() {
        Brand brand = createBrand(0, "brand");
        BrandResponseDto brandResponseDto = createBrandResponseDto(0, "brand");
        BrandRequestDto brandRequestDto = createBrandRequestDto("brand");

        when(brandRepository.save(brand)).thenReturn(brand);
        when(brand2BrandResponseDtoConverter.convert(brand)).thenReturn(brandResponseDto);
        when(brandRequestDto2BrandConverter.convert(brandRequestDto)).thenReturn(new Brand(0, "brand"));

        BrandResponseDto result = brandService.addNewBrand(brandRequestDto);

        assertNotNull(result);
        assertEquals(brandResponseDto, result);
    }

    @Test
    @DisplayName("Add New Brand Throws Entity Already Exists Exception")
    public void testAddNewBrand_whenBrandNameDoesExists_shouldReturnEntityAlreadyExistsException() {
        Brand brand = createBrand(0, "brand");
        BrandRequestDto brandRequestDto = createBrandRequestDto("brand");

        when(brandRepository.findByBrandName("brand")).thenReturn(Optional.of(brand));

        assertThrows(EntityAlreadyExistsException.class, () -> brandService.addNewBrand(brandRequestDto));
        Mockito.verifyNoInteractions(brand2BrandResponseDtoConverter, brandRequestDto2BrandConverter, brandResponseDto2BrandConverter);
    }

    @Test
    @DisplayName("Get Brand By Id")
    public void testGetByBrandId_whenBrandIdExists_shouldReturnBrand() {
        Brand brand = createBrand(1, "brand");
        BrandResponseDto brandResponseDto = createBrandResponseDto(1, "brand");

        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));
        when(brand2BrandResponseDtoConverter.convert(brand)).thenReturn(brandResponseDto);

        BrandResponseDto result = brandService.getBrandById(1);

        assertEquals(result, brandResponseDto);
    }

    @Test
    @DisplayName("Get By Brand Id Throws Entity Not Found Exception")
    public void testGetByBrandId_whenBrandIdDoesNotExist_shouldThrowEntityNotFoundException() {
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> brandService.getBrandById(1));

        Mockito.verifyNoInteractions(brand2BrandResponseDtoConverter, brandRequestDto2BrandConverter, brandResponseDto2BrandConverter);
    }

    @Test
    @DisplayName("Delete Brand By Id")
    public void testDeleteBrandById_whenBrandIdDoesExists_shouldDeleteBrand() {
        int id = 1;
        Brand brand = createBrand(id, "brand");
        when(brandRepository.findById(id)).thenReturn(Optional.of(brand));

        brandService.deleteBrand(id);
        verify(brandRepository).findById(id);
        verify(brandRepository).deleteById(id);
    }

    @Test
    @DisplayName("Delete Brand By Throws Entity Not Found Exception")
    public void testDeleteBrandById_whenBrandIdDoesNotExist_shouldThrowEntityNotFoundException() {
        int id = 1;
        when(brandRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> brandService.deleteBrand(id));
        Mockito.verifyNoInteractions(brand2BrandResponseDtoConverter, brandRequestDto2BrandConverter, brandResponseDto2BrandConverter);
    }

    @Test
    @DisplayName("Get Brand By Id")
    public void testGetByBrandName_whenBrandNameExists_shouldReturnBrand() {
        Brand brand = createBrand(1, "brand");
        BrandResponseDto brandResponseDto = createBrandResponseDto(1, "brand");

        when(brandRepository.findByBrandName("brand")).thenReturn(Optional.of(brand));
        when(brand2BrandResponseDtoConverter.convert(brand)).thenReturn(brandResponseDto);

        BrandResponseDto result = brandService.getBrandByName("brand");

        assertEquals(result, brandResponseDto);
    }

    @Test
    @DisplayName("Get By Brand Id Throws Entity Not Found Exception")
    public void testGetByBrandName_whenBrandNameDoesNotExist_shouldThrowEntityNotFoundException() {
        when(brandRepository.findByBrandName("brand")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> brandService.getBrandByName("brand"));

        Mockito.verifyNoInteractions(brand2BrandResponseDtoConverter, brandRequestDto2BrandConverter, brandResponseDto2BrandConverter);
    }

    @Test
    @DisplayName("Update Brand")
    public void testUpdateBrand_whenBrandNameDoesNotExist_shouldUpdateBrand() {
        Brand brand = createBrand(1, "brand");
        BrandRequestDto brandRequestDto = createBrandRequestDto("brand");
        BrandResponseDto brandResponseDto = createBrandResponseDto(1, "brand");

        when(brandRepository.findByBrandName("brand")).thenReturn(Optional.empty());
        when(brandRepository.findById(1)).thenReturn(Optional.of(brand));
        when(brandRepository.save(brand)).thenReturn(brand);
        when(brandRequestDto2BrandConverter.convert(1, brandRequestDto)).thenReturn(brand);
        when(brand2BrandResponseDtoConverter.convert(brand)).thenReturn(brandResponseDto);

        BrandResponseDto result = brandService.updateBrand(1, brandRequestDto);

        assertNotNull(result);
        assertEquals(brandResponseDto, result);
    }

    @Test
    @DisplayName("Update Brand Throws Entity Already Exists Exception")
    public void testUpdateBrand_whenBrandNameDoesExists_shouldThrowEntityAlreadyExistsException() {
        Brand brand = createBrand(1, "brand");
        BrandRequestDto brandRequestDto = createBrandRequestDto("brand");

        when(brandRepository.findByBrandName(brandRequestDto.getBrandName())).thenReturn(Optional.of(brand));


        assertThrows(EntityAlreadyExistsException.class, () -> brandService.updateBrand(1, brandRequestDto));

        Mockito.verifyNoInteractions(brand2BrandResponseDtoConverter, brandRequestDto2BrandConverter, brandResponseDto2BrandConverter);
    }

    @Test
    @DisplayName("Update Brand Throws Entity Not Found Exception")
    public void testUpdateBrand_whenBrandIdDoesNotExist_shouldThrowEntityNotFoundException() {
        BrandRequestDto brandRequestDto = createBrandRequestDto("brand");

        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> brandService.updateBrand(1, brandRequestDto));

        Mockito.verifyNoInteractions(brand2BrandResponseDtoConverter, brandRequestDto2BrandConverter, brandResponseDto2BrandConverter);
    }
}