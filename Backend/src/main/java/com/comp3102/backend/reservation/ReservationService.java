package com.comp3102.backend.reservation;


import com.comp3102.backend.car.Car;
import com.comp3102.backend.car.CarService;
import com.comp3102.backend.car.dto.converter.CarGetDetails2CarConverter;
import com.comp3102.backend.car.dto.response.CarGetDetailsDto;
import com.comp3102.backend.core.exceptions.BadRequestException;
import com.comp3102.backend.reservation.dto.converter.Reservation2ReservationResponseDtoConverter;
import com.comp3102.backend.reservation.dto.converter.ReservationRequestDto2ReservationConverter;
import com.comp3102.backend.reservation.dto.request.ReservationRequestDto;
import com.comp3102.backend.reservation.dto.response.ReservationResponseDto;
import com.comp3102.backend.reservation.dto.response.ReservationWithDetailsDto;
import com.comp3102.backend.user.User;
import com.comp3102.backend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.comp3102.backend.core.constant.ErrorConstant.*;
import static com.comp3102.backend.core.utils.GenericUtilities.calculateRemainingTimeToDeleteReservation;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationRequestDto2ReservationConverter reservationRequestDto2ReservationConverter;
    private final Reservation2ReservationResponseDtoConverter reservation2ReservationResponseDtoConverter;
    private final CarService carService;
    private final CarGetDetails2CarConverter carGetDetails2CarConverter;
    private final UserRepository userRepository;
    @Value("${reservation.scheduler.duration}")
    private int durationInMinutes;

    public List<ReservationResponseDto> getAll() {
        return reservation2ReservationResponseDtoConverter.convert(reservationRepository.findAll());
    }

    public ReservationResponseDto addNewReservation(ReservationRequestDto reservationRequest) {
        userRepository.findById(reservationRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_NOT_FOUND_MESSAGE, reservationRequest.getUserId())));
        Reservation reservation = reservationRequestDto2ReservationConverter.convert(reservationRequest);
        reservation.setTotalRentPrice(calculateReservationPrice(reservationRequest));
        return reservation2ReservationResponseDtoConverter.convert(reservationRepository.save(reservation));
    }

    public List<String> getNonAvailableCarsByCarId(Integer carId) {
        CarGetDetailsDto carDetails = carService.getCarById(carId);
        Car car = carGetDetails2CarConverter.convert(carDetails);

        return nonAvailableDateWorker(reservationRepository.findReservationsByCar(car));
    }

    public ReservationResponseDto getAvailableReservationsByReservationId(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(RESERVATION_NOT_FOUND_MESSAGE, String.valueOf(reservationId))));
        return reservation2ReservationResponseDtoConverter.convert(reservation);
    }

    private double calculateReservationPrice(ReservationRequestDto reservation) {
        long startDate = reservation.getPickupDate();
        long endDate = reservation.getDropOffDate();

        double dailyRentPrice = carService.getCarById(reservation.getCarId()).getDailyRentPrice();
        double totalRentPrice;
        if (startDate >= endDate) {
            throw new BadRequestException(START_DATE_BIGGER_THAN_END_DATE_ERROR_MESSAGE);
        } else {
            long differance = (endDate - startDate) / (24 * 60 * 60 * 1000);
            totalRentPrice = dailyRentPrice * differance;
            return totalRentPrice;
        }
    }

    private List<String> nonAvailableDateWorker(List<Reservation> reservationList) {
        List<String> datesBetween = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            LocalDateTime current = reservation.getPickUpDate();
            while (current.isBefore(reservation.getDropOffDate())) {
                datesBetween.add(current.toString());
                current = current.plusDays(1);
            }
        }
        return datesBetween;
    }


    public List<ReservationWithDetailsDto> getReservationsByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_NOT_FOUND_MESSAGE, userId)));
        List<Reservation> reservationList = reservationRepository.findAllByUser(user);
        List<ReservationWithDetailsDto> reservationWithDetailsDtos = new LinkedList<>();

        for (Reservation reservation : reservationList) {
            Car car = carService.getCarDetails(reservation.getCar().getCarId());

            reservationWithDetailsDtos.add(new ReservationWithDetailsDto(
                    reservation.getReservationId(),
                    reservation.getCar().getCarId(),
                    reservation.getBookedAt(),
                    reservation.getPickUpDate(),
                    reservation.getDropOffDate(),
                    reservation.getIsPaid(),
                    reservation.getTotalRentPrice(),
                    car.getColor().getColorName(),
                    car.getBrand().getBrandName(),
                    calculateRemainingTimeToDeleteReservation(reservation, durationInMinutes)
            ));
        }
        return reservationWithDetailsDtos;
    }


}
