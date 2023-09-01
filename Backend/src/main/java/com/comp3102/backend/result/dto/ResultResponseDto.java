package com.comp3102.backend.result.dto;


import com.comp3102.backend.result.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class ResultResponseDto {
    List<Result> results;
    DefaultStats defaultStats;
}
