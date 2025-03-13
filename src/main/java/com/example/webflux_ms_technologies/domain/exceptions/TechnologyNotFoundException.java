package com.example.webflux_ms_technologies.domain.exceptions;

public class TechnologyNotFoundException extends RuntimeException {
    public TechnologyNotFoundException(String message) {
        super(message);
    }
}
