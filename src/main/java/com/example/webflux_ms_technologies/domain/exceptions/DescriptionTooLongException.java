package com.example.webflux_ms_technologies.domain.exceptions;

public class DescriptionTooLongException extends RuntimeException {
    public DescriptionTooLongException(String message) {
        super(message);
    }
}
