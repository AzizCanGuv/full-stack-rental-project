package com.comp3102.backend.reservation.dto.converter;

import com.comp3102.backend.car.CarService;
import com.comp3102.backend.car.dto.converter.CarGetDetails2CarConverter;
import com.comp3102.backend.reservation.Reservation;
import com.comp3102.backend.reservation.dto.request.ReservationRequestDto;
import com.comp3102.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;

import static com.comp3102.backend.core.utils.GenericUtilities.longToLocalDateConvert;

@Component
@RequiredArgsConstructor
public class ReservationRequestDto2ReservationConverter {

    private final CarService carService;
    private final UserRepository userService;
    private final CarGetDetails2CarConverter carGetDetails2CarConverter;

    public Reservation convert(ReservationRequestDto from) {
        return new Reservation(null, LocalDateTime.now(),
                longToLocalDateConvert(from.getPickupDate()),
                longToLocalDateConvert(from.getDropOffDate()), null, false,
                carGetDetails2CarConverter.convert(carService.getCarById(from.getCarId())),
                userService.findById(from.getUserId()).orElseThrow());
    }
}
