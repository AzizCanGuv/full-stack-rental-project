package com.comp3102.backend.payment;

import com.comp3102.backend.payment.dto.request.PaymentFailedRequest;
import com.comp3102.backend.payment.dto.request.PaymentRequest;
import com.comp3102.backend.reservation.dto.response.ReservationResponseDto;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-payment-intent")
    @Hidden
    @SneakyThrows
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestBody PaymentRequest paymentRequest){
            Map<String, Object> paymentIntent = paymentService.createPaymentIntent(paymentRequest);
            return ResponseEntity.ok(paymentIntent);
    }
    @PostMapping("/failed-payment-intent")
    @Hidden
    public ResponseEntity<ReservationResponseDto> failedPaymentIntent(@RequestBody PaymentFailedRequest paymentFailedRequest) {
        return new ResponseEntity<>(paymentService.cancelPaymentIntent(paymentFailedRequest), HttpStatus.OK);
    }
}
