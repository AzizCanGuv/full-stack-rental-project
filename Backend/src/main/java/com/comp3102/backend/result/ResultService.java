package com.comp3102.backend.result;


import com.comp3102.backend.car.CarRepository;
import com.comp3102.backend.reservation.ReservationRepository;
import com.comp3102.backend.result.dto.DefaultStats;
import com.comp3102.backend.result.dto.ResultResponseDto;
import com.comp3102.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    public ResultResponseDto getAll() {
        return new ResultResponseDto(resultRepository.findAll(Sort.by(Sort.Order.asc("date"))),
                DefaultStats.builder()
                .totalCars(carRepository.count())
                .totalReservations(reservationRepository.countByIsPaidTrue())
                .totalUsers(userRepository.count()).build());
    }
}
