package com.andersen.cities.exception.badrequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CityNotFoundException extends ResponseStatusException {
    public CityNotFoundException(int cityId) {
        super(HttpStatus.BAD_REQUEST, String.format("City with id '%s' was not found", cityId));
    }
}