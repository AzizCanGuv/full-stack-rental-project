package com.comp3102.backend.car;


import com.comp3102.backend.car.dto.converter.CarGetDetails2CarConverter;
import com.comp3102.backend.core.constant.ErrorConstant;
import com.comp3102.backend.core.exceptions.BadRequestException;
import com.comp3102.backend.core.exceptions.EntityNotFoundException;
import com.comp3102.backend.car.dto.response.CarGetDetailsDto;
import com.comp3102.backend.car.dto.request.CarPostDetailsDto;
import com.comp3102.backend.car.dto.request.CarUpdateRequestDto;
import com.comp3102.backend.car.dto.converter.Car2CarGetDetailsDtoConverter;
import com.comp3102.backend.car.dto.converter.CarPostDetailsDto2CarConverter;
import com.comp3102.backend.brand.BrandRepository;
import com.comp3102.backend.color.ColorRepository;
import com.comp3102.backend.reservation.Reservation;
import com.comp3102.backend.reservation.ReservationRepository;
import com.comp3102.backend.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.comp3102.backend.core.constant.ErrorConstant.*;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final Car2CarGetDetailsDtoConverter car2CarGetDetailsDtoConverter;
    private final CarPostDetailsDto2CarConverter carPostDetailsDto2CarConverter;
    private final CarGetDetails2CarConverter carGetDetails2CarConverter;
    private final ColorRepository colorRepository;
    private final BrandRepository brandRepository;
    private final ReservationRepository reservationRepository;
    private final S3Service s3Service;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Value("${aws.region}")
    private String bucketRegion;
    @Value("${aws.s3.general.url.pattern}")
    private String bucketGeneralUrl;
    @Value("${aws.s3.car.picture.end.point}")
    private String carPictureEndPoint;


    public Page<CarGetDetailsDto> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return carRepository.findAllCarDetailsByPage(paging);
    }

    public Page<CarGetDetailsDto> getAllTest(Integer pageNo, Integer pageSize, String sortBy, Transmission transmissionType) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return carRepository.findAllCarDetailsByFilter(paging, transmissionType);
    }

    public Page<CarGetDetailsDto> getAllBySearchTest(String search, Integer pageNo, Integer pageSize, String sortBy, Transmission transmissionType) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return carRepository.findAllCarDetailsBySearch(paging, transmissionType, search);
    }

    public CarGetDetailsDto addNewCar(CarPostDetailsDto carDetails, MultipartFile file) {
        colorRepository.findByColorName(carDetails.getColorName()).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(COLOR_NOT_FOUND_MESSAGE, carDetails.getColorName())));
        brandRepository.findByBrandName(carDetails.getBrandName()).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(BRAND_NOT_FOUND_MESSAGE, carDetails.getBrandName())));
        Car car = carPostDetailsDto2CarConverter.convert(carDetails);
        car = carRepository.save(car);


        if (file != null) {
            uploadCarImage(car.getCarId(), file);
        }

        return car2CarGetDetailsDtoConverter.convert(car);
    }

    public CarGetDetailsDto getCarById(Integer id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(CAR_NOT_FOUND_MESSAGE, id)));
        return car2CarGetDetailsDtoConverter.convert(car);
    }

    public void deleteCar(Integer id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(CAR_NOT_FOUND_MESSAGE, id)));
        List<Reservation> reservationList = reservationRepository.findReservationsByCar(car);
        if (reservationList.size() > 0) {
            throw new BadRequestException("Car has been reserved!");
        }
        carRepository.deleteById(id);
    }

    public CarGetDetailsDto updateCar(Integer id, CarUpdateRequestDto carPostDetailsDto) {
        Car car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(CAR_NOT_FOUND_MESSAGE, id)));
        car.setDailyRentPrice(carPostDetailsDto.getDailyRentPrice());
        car.setDescription(carPostDetailsDto.getDescription());
        car.setLocation(carPostDetailsDto.getLocation());
        carRepository.save(car);
        return car2CarGetDetailsDtoConverter.convert(car);
    }

    public List<CarGetDetailsDto> getCarsByBookingStatus(Boolean isBooked) {
        List<Car> carList = carRepository.findCarsByIsBooked(isBooked);
        return car2CarGetDetailsDtoConverter.convert(carList);
    }

    public CarGetDetailsDto updateCarBookingStatus(Integer id, Boolean isBooked) {
        Car car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(CAR_NOT_FOUND_MESSAGE, id)));
        car.setIsBooked(isBooked);
        carRepository.save(car);
        return car2CarGetDetailsDtoConverter.convert(car);
    }

    public Car getCarDetails(Integer id) {
        CarGetDetailsDto car = carRepository.getCarDetails(id).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(CAR_NOT_FOUND_MESSAGE, id)));
        return carGetDetails2CarConverter.convert(car);
    }

    public void uploadCarImage(Integer carId, MultipartFile file) {


        Car car = carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(CAR_NOT_FOUND_MESSAGE, carId)));
        String carImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    bucketName,
                    carPictureEndPoint.formatted(carId, carImageId),
                    file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String pictureUrl = String.format("%s%s", bucketGeneralUrl, carPictureEndPoint).formatted(bucketName, bucketRegion, carId, carImageId);
        car.setCarImageId(pictureUrl);
        carRepository.save(car);
    }
}