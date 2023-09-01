package com.comp3102.backend.reservation.dto.converter;

import com.comp3102.backend.car.CarService;
import com.comp3102.backend.car.dto.converter.CarGetDetails2CarConverter;
import com.comp3102.backend.reservation.Reservation;
import com.comp3102.backend.reservation.dto.response.ReservationResponseDto;
import com.comp3102.backend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ReservationResponseDto2ReservationConverter {
    private final CarService carService;
    private final UserRepository userService;
    private final CarGetDetails2CarConverter carGetDetails2CarConverter;
    public Reservation convert(ReservationResponseDto from) {
        return new Reservation(from.getReservationId(), from.getBookedAt(),
                from.getPickUpDate(),
                from.getDropOffDate(),
                from.getTotalRentPrice(),
                from.isPaid(),
                carGetDetails2CarConverter.convert(carService.getCarById(from.getCarId())),
                userService.findById(from.getUserId()).orElseThrow(() -> new EntityNotFoundException("User Not Found!!")));
    }

    public List<Reservation> convert(List<ReservationResponseDto> from) {
        return from.stream().map(r -> new Reservation(
                r.getReservationId(),
                r.getBookedAt(),
                r.getPickUpDate(),
                r.getDropOffDate(),
                r.getTotalRentPrice(),
                r.isPaid(),
                carGetDetails2CarConverter.convert(carService.getCarById(r.getCarId())),
                userService.findById(r.getUserId()).orElseThrow(() -> new EntityNotFoundException("User Not Found!!")))).collect(Collectors.toList());
    }
}
