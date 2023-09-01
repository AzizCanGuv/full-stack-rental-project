package com.comp3102.backend.reservation;

import com.comp3102.backend.car.Car;
import com.comp3102.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findAllByUser(User user);
    List<Reservation> findReservationsByCar(Car car);

    List<Reservation> findReservationsByIsPaidTrue();
    long countByIsPaidTrue();
    List<Reservation> findByBookedAtBeforeAndIsPaidFalse(LocalDateTime deleteTime);

}
