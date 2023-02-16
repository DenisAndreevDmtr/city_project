package com.andersen.cities.exception.forbidden;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.andersen.cities.util.Constants.INCORRECT_CREDENTIALS_MESSAGE;

public class IncorrectCredentialsException extends ResponseStatusException {
    public IncorrectCredentialsException() {
        super(HttpStatus.FORBIDDEN, INCORRECT_CREDENTIALS_MESSAGE);
    }
}