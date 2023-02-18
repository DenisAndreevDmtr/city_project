package com.andersen.cities.advice;

import com.andersen.cities.dto.response.GeneralErrorResponse;
import com.andersen.cities.dto.response.ResponseMessageConstant;
import com.andersen.cities.exception.badrequest.IncorrectFileInformationException;
import com.andersen.cities.exception.badrequest.CityNotFoundException;
import com.andersen.cities.exception.badrequest.FileNotFoundException;
import com.andersen.cities.exception.forbidden.IncorrectCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.Instant;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            IOException.class
    })
    public ResponseEntity<GeneralErrorResponse> handleIOException(ResponseStatusException ex) {
        GeneralErrorResponse errorResponse = createGeneralErrorResponse(ResponseMessageConstant.INCORRECT_FILE_INFORMATION);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            IncorrectFileInformationException.class,
            FileNotFoundException.class,
            CityNotFoundException.class
    })
    public ResponseEntity<GeneralErrorResponse> handleAllBadRequestException(ResponseStatusException ex) {
        GeneralErrorResponse errorResponse = createGeneralErrorResponse(ex.getReason());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            IncorrectCredentialsException.class
    })
    public ResponseEntity<GeneralErrorResponse> handleAllForbiddenException(ResponseStatusException ex) {
        GeneralErrorResponse errorResponse = createGeneralErrorResponse(ex.getReason());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    private GeneralErrorResponse createGeneralErrorResponse(String message) {
        return GeneralErrorResponse.builder()
                .dateTime(Instant.now())
                .message(message)
                .build();
    }
}