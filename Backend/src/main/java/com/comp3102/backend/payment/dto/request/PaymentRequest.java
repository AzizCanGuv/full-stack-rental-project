package com.comp3102.backend.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private UUID reservationId;
    private Long amount;
    private String currency;
    private String paymentMethodId;
}
