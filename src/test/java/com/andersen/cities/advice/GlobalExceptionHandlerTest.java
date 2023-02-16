package com.andersen.cities.advice;

import com.andersen.cities.dto.response.GeneralErrorResponse;
import com.andersen.cities.exception.badrequest.FileNotFoundException;
import com.andersen.cities.exception.badrequest.IncorrectFileInformationException;
import com.andersen.cities.exception.forbidden.IncorrectCredentialsException;
import com.andersen.cities.exception.nocontent.NothingFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.stream.Stream;

import static com.andersen.cities.util.Constants.FILE_NOT_FOUND_MESSAGE;
import static com.andersen.cities.util.Constants.INCORRECT_CREDENTIALS_MESSAGE;
import static com.andersen.cities.util.Constants.INCORRECT_FILE_INFO_MESSAGE;
import static com.andersen.cities.util.Constants.NO_CONTENT_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
        GlobalExceptionHandler.class

})
public class GlobalExceptionHandlerTest {
    @InjectMocks
    private final GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    public GlobalExceptionHandlerTest(GlobalExceptionHandler globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @ParameterizedTest
    @MethodSource("parametersForHandleAllBadRequestExceptions")
    public void shouldReturnResponseBadRequest_whenHandleAllBadRequestException(ResponseStatusException ex, String message) {
        ResponseEntity<GeneralErrorResponse> actual = globalExceptionHandler.handleAllBadRequestException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertEquals(message, Objects.requireNonNull(actual.getBody()).getMessage());
    }

    private static Stream<Arguments> parametersForHandleAllBadRequestExceptions() {
        return Stream.of(Arguments.of(new IncorrectFileInformationException(), INCORRECT_FILE_INFO_MESSAGE),
                Arguments.of(new FileNotFoundException(), FILE_NOT_FOUND_MESSAGE));
    }

    @Test
    public void shouldReturnResponseForbidden_whenHandleAllForbiddenException() {
        ResponseEntity<GeneralErrorResponse> actual = globalExceptionHandler.handleAllForbiddenException(new IncorrectCredentialsException());

        assertEquals(HttpStatus.FORBIDDEN, actual.getStatusCode());
        assertEquals(INCORRECT_CREDENTIALS_MESSAGE, Objects.requireNonNull(actual.getBody()).getMessage());
    }

    @Test
    public void shouldReturnResponseNoContent_whenHandleAllNotContentException() {
        ResponseEntity<GeneralErrorResponse> actual = globalExceptionHandler.handleAllNotContentException(new NothingFoundException());

        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertEquals(NO_CONTENT_MESSAGE, Objects.requireNonNull(actual.getBody()).getMessage());
    }
}