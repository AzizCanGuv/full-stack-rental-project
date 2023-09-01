package com.comp3102.backend.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
public class GenericExceptionSignature {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final int errorCode;
    private final String errorMessage;
}
