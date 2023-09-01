package com.comp3102.backend.result;

import com.comp3102.backend.result.dto.ResultResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
@CrossOrigin
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    @Operation(
            description = "Get all statistics",
            summary = "Get all statistics",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResultResponseDto> getAllStats() {
        return ResponseEntity.ok(resultService.getAll());
    }


}
