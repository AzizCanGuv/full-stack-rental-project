package com.comp3102.backend.car;

import com.comp3102.backend.car.dto.response.CarGetDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("Select new com.comp3102.backend.car.dto.response.CarGetDetailsDto(c.carId, b.brandName, col.colorName, c.description, c.dailyRentPrice, c.enginePower, c.year, c.location, c.createdAt, c.transmissionType, c.isBooked, c.carImageId) "
            + "From Car c Inner Join c.brand b Inner Join c.color col where c.carId = :id")
    Optional<CarGetDetailsDto> getCarDetails(Integer id);


    @Query("SELECT new com.comp3102.backend.car.dto.response.CarGetDetailsDto(c.carId, b.brandName, col.colorName, c.description, c.dailyRentPrice, c.enginePower, c.year, c.location, c.createdAt, c.transmissionType, c.isBooked, c.carImageId) "
            + "FROM Car c INNER JOIN c.brand b INNER JOIN c.color col WHERE c.transmissionType = :transmissionType AND b.brandName LIKE %:search%")
    Page<CarGetDetailsDto> findAllCarDetailsBySearch(Pageable pageable, @Param("transmissionType") Transmission transmissionType, @Param("search") String search);
    @Query("SELECT new com.comp3102.backend.car.dto.response.CarGetDetailsDto(c.carId, b.brandName, col.colorName, c.description, c.dailyRentPrice, c.enginePower, c.year, c.location, c.createdAt, c.transmissionType, c.isBooked, c.carImageId) "
            + "FROM Car c INNER JOIN c.brand b INNER JOIN c.color col WHERE c.transmissionType = :transmissionType")
    Page<CarGetDetailsDto> findAllCarDetailsByFilter(Pageable pageable, @Param("transmissionType") Transmission transmissionType);


    List<Car> findCarsByIsBooked(Boolean isBooked);
    @Query("Select new com.comp3102.backend.car.dto.response.CarGetDetailsDto(c.carId, b.brandName, col.colorName, c.description, c.dailyRentPrice, c.enginePower, c.year, c.location, c.createdAt, c.transmissionType, c.isBooked, c.carImageId) "
            + "From Car c Inner Join c.brand b Inner Join c.color col")
    Page<CarGetDetailsDto> findAllCarDetailsByPage(Pageable pageable);
}