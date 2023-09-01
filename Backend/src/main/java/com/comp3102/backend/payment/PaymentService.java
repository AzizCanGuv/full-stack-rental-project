package com.comp3102.backend.payment;

import com.comp3102.backend.core.constant.EmailConstants;
import com.comp3102.backend.core.mailConfiguration.EmailSenderService;
import com.comp3102.backend.payment.dto.request.PaymentFailedRequest;
import com.comp3102.backend.payment.dto.request.PaymentRequest;
import com.comp3102.backend.reservation.Reservation;
import com.comp3102.backend.reservation.ReservationRepository;
import com.comp3102.backend.reservation.dto.converter.Reservation2ReservationResponseDtoConverter;
import com.comp3102.backend.reservation.dto.response.ReservationResponseDto;
import com.comp3102.backend.user.User;
import com.comp3102.backend.user.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.comp3102.backend.core.constant.ErrorConstant.*;

@Service
public class PaymentService {

    private final EmailSenderService mailService;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final Reservation2ReservationResponseDtoConverter reservationResponseDto2ReservationConverter;

    public PaymentService(ReservationRepository reservationRepository, @Value("${stripe.secret.key}") String apiKey, EmailSenderService mailService, UserRepository userRepository, Reservation2ReservationResponseDtoConverter reservationResponseDto2ReservationConverter) {
        this.reservationRepository = reservationRepository;
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.reservationResponseDto2ReservationConverter = reservationResponseDto2ReservationConverter;
        Stripe.apiKey = apiKey;
    }


    public Map<String, Object> createPaymentIntent(PaymentRequest paymentRequest) {
        try {
            PaymentIntentCreateParams params = new PaymentIntentCreateParams.Builder()
                    .setAmount(paymentRequest.getAmount())
                    .setCurrency(paymentRequest.getCurrency())
                    .build();

            Reservation reservation = reservationRepository.findById(paymentRequest.getReservationId()).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(RESERVATION_NOT_FOUND_MESSAGE, String.valueOf(paymentRequest.getReservationId()))));
            reservation.setIsPaid(true);
            reservationRepository.save(reservation);
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            Map<String, Object> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());

            User user = userRepository.findById(reservation.getUser().getId()).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(USER_NOT_FOUND_MESSAGE, reservation.getUser().getId())));

            mailService.sendMail(user.getEmail(), EmailConstants.reservationApproved(user.getFirstName(), reservation.getCar().getBrand().getBrandName(), reservation.getPickUpDate(), reservation.getDropOffDate(), reservation.getTotalRentPrice()), EmailConstants.RESERVATION_APPROVED_SUBJECT);
            return response;
        } catch (StripeException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public ReservationResponseDto cancelPaymentIntent(PaymentFailedRequest paymentFailedRequest) {
        Reservation reservation = reservationRepository.findById(paymentFailedRequest.getReservationId()).orElseThrow(() -> new EntityNotFoundException(errorMessageParser(RESULT_NOT_FOUND_MESSAGE, String.valueOf(paymentFailedRequest.getReservationId()))));
        reservation.setIsPaid(false);
        reservationRepository.save(reservation);
        return reservationResponseDto2ReservationConverter.convert(reservation);
    }
}
