package com.taxicalls.routes.resource.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RouteNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RouteNotFoundException(Integer id) {
        super("No such route: " + id);
    }
}
