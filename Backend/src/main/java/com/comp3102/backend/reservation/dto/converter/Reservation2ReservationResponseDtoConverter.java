package com.comp3102.backend.reservation.dto.converter;

import com.comp3102.backend.reservation.Reservation;
import com.comp3102.backend.reservation.dto.response.ReservationResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.comp3102.backend.core.utils.GenericUtilities.calculateRemainingTimeToDeleteReservation;

@Component
public class Reservation2ReservationResponseDtoConverter {

    @Value("${reservation.scheduler.duration}")
    private int durationInMinutes;

    public ReservationResponseDto convert(Reservation from) {
        return new ReservationResponseDto(
                from.getReservationId(),
                from.getUser().getId(),
                from.getCar().getCarId(),
                from.getBookedAt(),
                from.getPickUpDate(),
                from.getDropOffDate(),
                from.getIsPaid(),
                from.getTotalRentPrice(),
                calculateRemainingTimeToDeleteReservation(from, durationInMinutes));
    }

    public List<ReservationResponseDto> convert(List<Reservation> from) {
        return from.stream().map(r -> new ReservationResponseDto(
                        r.getReservationId(),
                        r.getUser().getId(),
                        r.getCar().getCarId(),
                        r.getBookedAt(),
                        r.getPickUpDate(),
                        r.getDropOffDate(),
                        r.getIsPaid(),
                        r.getTotalRentPrice(),
                        calculateRemainingTimeToDeleteReservation(r, durationInMinutes))).
                collect(Collectors.toList());
    }
}
