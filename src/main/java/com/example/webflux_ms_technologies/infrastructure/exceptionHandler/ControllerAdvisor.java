package com.example.webflux_ms_technologies.infrastructure.exceptionHandler;

import com.example.webflux_ms_technologies.domain.exceptions.DescriptionTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.NameTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.TechnologyAlreadyExistsException;
import com.example.webflux_ms_technologies.domain.exceptions.TechnologyNotFoundException;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.exceptions.SomeTechnologiesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(NameTooLongException.class)
    public ResponseEntity<ExceptionResponse> handleNameTooLongException(NameTooLongException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(DescriptionTooLongException.class)
    public ResponseEntity<ExceptionResponse> handleDescriptionTooLongException(DescriptionTooLongException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(TechnologyAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleTechnologyAlreadyExistsException(TechnologyAlreadyExistsException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(TechnologyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTechnologyNotFoundException(TechnologyNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
    @ExceptionHandler(SomeTechnologiesNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleSomeTechnologiesNotFoundException(SomeTechnologiesNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
}
