package com.andersen.cities.exception.badrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.andersen.cities.util.Constants.INCORRECT_FILE_INFO_MESSAGE;

public class IncorrectFileInformationException extends ResponseStatusException {
    public IncorrectFileInformationException() {
        super(HttpStatus.BAD_REQUEST, INCORRECT_FILE_INFO_MESSAGE);
    }
}