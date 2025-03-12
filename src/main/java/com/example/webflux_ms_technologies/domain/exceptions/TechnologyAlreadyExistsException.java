package com.example.webflux_ms_technologies.domain.exceptions;

public class TechnologyAlreadyExistsException extends RuntimeException {
    public TechnologyAlreadyExistsException(String message) {
        super(message);
    }
}
