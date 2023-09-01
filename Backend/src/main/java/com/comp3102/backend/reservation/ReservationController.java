package com.comp3102.backend.reservation;

import com.comp3102.backend.reservation.dto.request.ReservationRequestDto;
import com.comp3102.backend.reservation.dto.response.ReservationResponseDto;
import com.comp3102.backend.reservation.dto.response.ReservationWithDetailsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@CrossOrigin
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/getAll")
    @Operation(
            description = "Get all reservations",
            summary = "Get all reservations",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<ReservationResponseDto>> getAll() {
        return new ResponseEntity<>(reservationService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/reserve")
    @Operation(
            description = "Add a new reservation",
            summary = "Add new reservation",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ReservationResponseDto> addNewReservation(@RequestBody ReservationRequestDto reservationRequest) throws MessagingException {
        return new ResponseEntity<>(reservationService.addNewReservation(reservationRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getNonAvailableDates/{carId}")
    @Operation(
            description = "Get non-available reservation dates for a car",
            summary = "Get non-available reservation dates",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public List<String> getAvailableReservationsByCarId(@PathVariable Integer carId) {
        return reservationService.getNonAvailableCarsByCarId(carId);
    }

    @GetMapping("/getReservationByReservationId/{reservationId}")
    @Operation(
            description = "Get a reservation by reservation ID",
            summary = "Get reservation by reservation ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ReservationResponseDto getAvailableReservationsByReservationId(@PathVariable UUID reservationId) {
        return reservationService.getAvailableReservationsByReservationId(reservationId);
    }

    @GetMapping("/getReservationsByUserId/{userId}")
    @Operation(
            description = "Get reservations by user ID",
            summary = "Get reservations by user ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public List<ReservationWithDetailsDto> getReservationsByUserId(@PathVariable Integer userId) {
        return reservationService.getReservationsByUserId(userId);
    }
}
