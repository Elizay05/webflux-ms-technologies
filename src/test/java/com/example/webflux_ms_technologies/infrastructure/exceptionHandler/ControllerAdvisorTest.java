package com.example.webflux_ms_technologies.infrastructure.exceptionHandler;

import com.example.webflux_ms_technologies.domain.exceptions.DescriptionTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.NameTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.TechnologyAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ControllerAdvisorTest {

    @Test
    public void testNameTooLongException() {
        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
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
        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
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
        // Arrange
        ControllerAdvisor controllerAdvisor = new ControllerAdvisor();
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
}
