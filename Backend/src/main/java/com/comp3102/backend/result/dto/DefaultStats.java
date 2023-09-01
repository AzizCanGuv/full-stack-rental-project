package com.comp3102.backend.result.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefaultStats {
    Long totalCars;
    Long totalReservations;
    Long totalUsers;
}
