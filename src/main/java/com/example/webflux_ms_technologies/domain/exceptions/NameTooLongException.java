package com.example.webflux_ms_technologies.domain.exceptions;

public class NameTooLongException extends RuntimeException {
    public NameTooLongException(String message) {
        super(message);
    }
}
