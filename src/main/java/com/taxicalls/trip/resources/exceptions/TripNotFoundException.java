package com.taxicalls.trip.resources.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TripNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TripNotFoundException(Integer id) {
        super("No such entity: " + id);
    }
}