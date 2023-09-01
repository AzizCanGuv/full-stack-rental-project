package com.comp3102.backend.car;

import com.comp3102.backend.car.dto.response.CarGetDetailsDto;
import com.comp3102.backend.car.dto.request.CarPostDetailsDto;
import com.comp3102.backend.car.dto.request.CarUpdateRequestDto;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/getAll")
    @Operation(
            description = "Retrieve all cars with pagination and sorting",
            summary = "Get all cars with pagination and sorting",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Page<CarGetDetailsDto>> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(defaultValue = "carId") String sortBy) {
        Page<CarGetDetailsDto> result = carService.getAll(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll/test")
    @Operation(
            description = "Retrieve all brands with applied filter",
            summary = "Get all brands with filter",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Page<CarGetDetailsDto>> getAllBrandsByAppliedFilter(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "carId") String sortBy,
            @RequestParam(defaultValue = "AUTOMATIC") Transmission transmissionType) {

        Page<CarGetDetailsDto> result;
        if (StringUtils.isEmpty(search)) {
            result = carService.getAllTest(pageNo, pageSize, sortBy, transmissionType);
        } else {
            result = carService.getAllBySearchTest(search, pageNo, pageSize, sortBy, transmissionType);
        }

        return ResponseEntity.ok(result);
    }

    @Operation(
            description = "Add a new car",
            summary = "Add new car",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CarGetDetailsDto> addNewCar(@RequestPart("car") @Valid CarPostDetailsDto car,
                                                      @RequestPart("file") MultipartFile file) {
        return new ResponseEntity<>(carService.addNewCar(car, file), HttpStatus.CREATED);
    }

    @Operation(
            description = "Retrieve a car by ID",
            summary = "Get car by ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/getById/{id}")
    public ResponseEntity<CarGetDetailsDto> getCarById(@PathVariable Integer id) {
        return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            description = "Delete a car by ID",
            summary = "Delete car by ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public void deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
    }

    @PutMapping("/edit/{id}")
    @Operation(
            description = "Update a car by ID",
            summary = "Update car by ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<CarGetDetailsDto> updateCar(@PathVariable Integer id, @RequestBody @Valid CarUpdateRequestDto car) {
        return new ResponseEntity<>(carService.updateCar(id, car), HttpStatus.OK);
    }

    @GetMapping("/status/{isBooked}")
    @Operation(
            description = "Retrieve cars by booking status",
            summary = "Get cars by booking status",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<CarGetDetailsDto>> getCarsByBookingStatus(@PathVariable Boolean isBooked) {
        return new ResponseEntity<>(carService.getCarsByBookingStatus(isBooked), HttpStatus.OK);
    }

    @PutMapping("/status/{id}/{isBooked}")
    @Operation(
            description = "Update car booking status by ID",
            summary = "Update car booking status by ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<CarGetDetailsDto> updateCarBookingStatus(@PathVariable Integer id, @PathVariable Boolean isBooked) {
        return new ResponseEntity<>(carService.updateCarBookingStatus(id, isBooked), HttpStatus.OK);
    }

    @PostMapping(value = "/car-image/{carId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            description = "Upload car image by car ID",
            summary = "Upload car image by car ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public void uploadCarImage(@PathVariable("carId") Integer carId, @RequestParam("file") MultipartFile file) {
        carService.uploadCarImage(carId, file);
    }
}