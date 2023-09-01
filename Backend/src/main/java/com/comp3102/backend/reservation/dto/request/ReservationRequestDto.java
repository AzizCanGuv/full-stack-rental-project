package com.comp3102.backend.reservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequestDto {

    private Integer userId;
    private Integer carId;
    private long pickupDate;
    private long  dropOffDate;


}
