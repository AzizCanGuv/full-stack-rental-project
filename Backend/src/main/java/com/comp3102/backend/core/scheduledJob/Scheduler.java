package com.comp3102.backend.core.scheduledJob;

import com.comp3102.backend.reservation.Reservation;
import com.comp3102.backend.reservation.ReservationRepository;
import com.comp3102.backend.result.Result;
import com.comp3102.backend.result.ResultRepository;
import com.comp3102.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ReservationRepository reservationRepository;

    @Value("${reservation.scheduler.duration}")
    private int durationInMinutes;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    @Scheduled(fixedDelay = 10000) // Run every 10 seconds
    public void checkReservations() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime deleteTime = currentDateTime.minusMinutes(durationInMinutes);

        List<Reservation> reservationsToDelete = reservationRepository.findByBookedAtBeforeAndIsPaidFalse(deleteTime);

        for (Reservation reservation : reservationsToDelete) {
            reservationRepository.deleteById(reservation.getReservationId());
        }
    }


    @Scheduled(fixedDelay = 900000) // Run every day at 00:02 cron = "2 0 * * * *"
    public void recordResults() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm", Locale.ENGLISH);
        String dateTimeRecord = currentDateTime.format(formatter);
        double totalIncome = 0;
        for (Reservation reservation : reservationRepository.findReservationsByIsPaidTrue()) {
            totalIncome += reservation.getTotalRentPrice();
        }
        resultRepository.save(Result.builder().date(dateTimeRecord).totalIncome(totalIncome).build());
    }
}
