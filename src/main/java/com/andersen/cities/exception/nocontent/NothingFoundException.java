package com.andersen.cities.exception.nocontent;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.andersen.cities.util.Constants.NO_CONTENT_MESSAGE;

public class NothingFoundException extends ResponseStatusException {
    public NothingFoundException() {
        super(HttpStatus.NO_CONTENT, NO_CONTENT_MESSAGE);
    }
}