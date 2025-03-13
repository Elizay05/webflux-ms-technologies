package com.example.webflux_ms_technologies.infrastructure.input.handler;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyIdsRequest;
import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.application.dto.response.TechnologyPageResponse;
import com.example.webflux_ms_technologies.application.dto.response.TechnologyResponse;
import com.example.webflux_ms_technologies.application.handler.ITechnologyRestHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TechnologyHandlerTest {

    @Mock
    private ITechnologyRestHandler technologyRestHandler;

    @Mock
    private Validator validator;

    @InjectMocks
    private TechnologyHandler technologyHandler;

    @Test
    public void test_valid_technology_request_returns_created_status() {
        // Arrange
        TechnologyRequest validRequest = new TechnologyRequest("Java", "Programming language");
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(validRequest));

        when(validator.validate(validRequest)).thenReturn(Collections.emptySet());
        when(technologyRestHandler.createTechnology(validRequest)).thenReturn(Mono.empty());

        // Act
        Mono<ServerResponse> responseMono = technologyHandler.createTechnology(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode() == HttpStatus.CREATED)
                .verifyComplete();

        verify(technologyRestHandler).createTechnology(validRequest);
    }

    @Test
    public void test_empty_body_request_returns_bad_request() {
        TechnologyRequest invalidRequest = new TechnologyRequest("", "");
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(invalidRequest));

        Set<ConstraintViolation<TechnologyRequest>> violations = new HashSet<>();
        ConstraintViolation<TechnologyRequest> nameViolation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);

        when(path.toString()).thenReturn("name");
        when(nameViolation.getPropertyPath()).thenReturn(path);
        when(nameViolation.getMessage()).thenReturn("is required");
        violations.add(nameViolation);

        when(validator.validate(invalidRequest)).thenReturn(violations);

        // Act
        Mono<ServerResponse> responseMono = technologyHandler.createTechnology(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> {
                    return response.statusCode() == HttpStatus.BAD_REQUEST &&
                            response.headers().getContentType().equals(MediaType.APPLICATION_JSON);
                })
                .verifyComplete();

        verify(validator).validate(invalidRequest);
        verify(technologyRestHandler, Mockito.never()).createTechnology(invalidRequest);
    }

    @Test
    public void test_get_technologies_returns_ok_response() {
        // Arrange
        ITechnologyRestHandler technologyRestHandler = mock(ITechnologyRestHandler.class);
        Validator validator = mock(Validator.class);
        TechnologyHandler technologyHandler = new TechnologyHandler(technologyRestHandler, validator);

        ServerRequest request = mock(ServerRequest.class);
        TechnologyPageResponse pageResponse = new TechnologyPageResponse();

        when(technologyRestHandler.getTechnologies(request)).thenReturn(Mono.just(pageResponse));

        // Act
        Mono<ServerResponse> result = technologyHandler.getTechnologies(request);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();

        verify(technologyRestHandler).getTechnologies(request);
    }

    @Test
    public void test_getTechnologiesByIds_validRequest_returnsOk() {
        // Arrange
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        TechnologyIdsRequest validRequest = new TechnologyIdsRequest(ids);
        List<TechnologyResponse> mockResponse = Arrays.asList(
                new TechnologyResponse(1L, "Java", "Programming language"),
                new TechnologyResponse(2L, "Python", "Scripting language")
        );

        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(validRequest));

        when(technologyRestHandler.getTechnologiesByIds(ids)).thenReturn(Mono.just(mockResponse));

        // Act
        Mono<ServerResponse> responseMono = technologyHandler.getTechnologiesByIds(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode() == HttpStatus.OK)
                .verifyComplete();

        verify(technologyRestHandler).getTechnologiesByIds(ids);
    }

    @Test
    public void test_getTechnologiesByIds_emptyIds_returnsBadRequest() {
        // Arrange
        TechnologyIdsRequest invalidRequest = new TechnologyIdsRequest(Collections.emptyList());
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(invalidRequest));

        // Act
        Mono<ServerResponse> responseMono = technologyHandler.getTechnologiesByIds(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode() == HttpStatus.BAD_REQUEST &&
                        response.headers().getContentType().equals(MediaType.APPLICATION_JSON))
                .verifyComplete();

        verify(technologyRestHandler, Mockito.never()).getTechnologiesByIds(Mockito.any());
    }
}
