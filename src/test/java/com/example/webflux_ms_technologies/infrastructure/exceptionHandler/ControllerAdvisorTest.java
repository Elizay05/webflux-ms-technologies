package com.example.webflux_ms_technologies.infrastructure.exceptionHandler;

import com.example.webflux_ms_technologies.domain.exceptions.DescriptionTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.NameTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.TechnologyAlreadyExistsException;
import com.example.webflux_ms_technologies.domain.exceptions.TechnologyNotFoundException;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.exceptions.SomeTechnologiesNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ControllerAdvisorTest {

    @InjectMocks
    private ControllerAdvisor controllerAdvisor;

    @Test
    public void testNameTooLongException() {
        String errorMessage = "Technology name is too long";
        NameTooLongException exception = new NameTooLongException(errorMessage);

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleNameTooLongException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ExceptionResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(errorMessage, response.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    public void testDescriptionTooLongException() {
        String errorMessage = "Description is too long";
        DescriptionTooLongException exception = new DescriptionTooLongException(errorMessage);

        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleDescriptionTooLongException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(errorMessage, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getStatus());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }

    @Test
    public void testTechnologyAlreadyExistsException() {
        String errorMessage = "Technology already exists";
        TechnologyAlreadyExistsException exception = new TechnologyAlreadyExistsException(errorMessage);

        // Act
        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleTechnologyAlreadyExistsException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(errorMessage, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), responseEntity.getBody().getStatus());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }

    @Test
    public void testTechnologyNotFoundException() {
        String errorMessage = "Technology not found";
        TechnologyNotFoundException exception = new TechnologyNotFoundException(errorMessage);

        // Act
        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleTechnologyNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(errorMessage, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.toString(), responseEntity.getBody().getStatus());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }

    @Test
    public void testSomeTechnologiesNotFoundException() {
        String errorMessage = "Some technologies not found";
        SomeTechnologiesNotFoundException exception = new SomeTechnologiesNotFoundException(errorMessage);

        // Act
        ResponseEntity<ExceptionResponse> responseEntity = controllerAdvisor.handleSomeTechnologiesNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(errorMessage, responseEntity.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.toString(), responseEntity.getBody().getStatus());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }
}
