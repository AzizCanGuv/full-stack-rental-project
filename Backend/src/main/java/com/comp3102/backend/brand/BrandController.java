package com.comp3102.backend.brand;

import com.comp3102.backend.brand.dto.request.BrandRequestDto;
import com.comp3102.backend.brand.dto.response.BrandResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@CrossOrigin
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    @Operation(description = "Get All", summary = "Get All Brands", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<BrandResponseDto>> getAll() {
        return new ResponseEntity<>(brandService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(description = "Add New Brand to the DB", summary = "Add New Brand", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BrandResponseDto> addNewBrand(@RequestBody @Valid BrandRequestDto brand) {
        return new ResponseEntity<>(brandService.addNewBrand(brand), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get Brand By Id", summary = "Get Brand By Id", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BrandResponseDto> getBrandById(@PathVariable Integer id) {
        return new ResponseEntity<>(brandService.getBrandById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete Brand By Id", summary = "Delete Brand By Id", security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteBrand(@PathVariable Integer id) {
        brandService.deleteBrand(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update Brand By Id", summary = "Update Brand By Id", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BrandResponseDto> updateBrand(@PathVariable Integer id, @RequestBody @Valid BrandRequestDto brand) {
        return new ResponseEntity<>(brandService.updateBrand(id, brand), HttpStatus.OK);
    }
}