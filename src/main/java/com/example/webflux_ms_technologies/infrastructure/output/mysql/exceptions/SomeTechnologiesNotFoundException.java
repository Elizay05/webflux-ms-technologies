package com.example.webflux_ms_technologies.infrastructure.output.mysql.exceptions;

public class SomeTechnologiesNotFoundException extends RuntimeException {
    public SomeTechnologiesNotFoundException(String message) {
        super(message);
    }
}
