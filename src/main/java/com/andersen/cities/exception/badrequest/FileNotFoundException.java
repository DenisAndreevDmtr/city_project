package com.andersen.cities.exception.badrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static com.andersen.cities.util.Constants.FILE_NOT_FOUND_MESSAGE;

public class FileNotFoundException extends ResponseStatusException {
    public FileNotFoundException() {
        super(HttpStatus.BAD_REQUEST, FILE_NOT_FOUND_MESSAGE);
    }
}