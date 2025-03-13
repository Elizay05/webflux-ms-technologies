package com.example.webflux_ms_technologies.infrastructure.input.handler;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.application.handler.ITechnologyRestHandler;
import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.domain.model.TechnologyPageModel;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Mockito.when(validator.validate(validRequest)).thenReturn(Collections.emptySet());
        Mockito.when(technologyRestHandler.createTechnology(validRequest)).thenReturn(Mono.empty());

        // Act
        Mono<ServerResponse> responseMono = technologyHandler.createTechnology(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.statusCode() == HttpStatus.CREATED)
                .verifyComplete();

        Mockito.verify(technologyRestHandler).createTechnology(validRequest);
    }

    @Test
    public void test_empty_body_request_returns_bad_request() {
        TechnologyRequest invalidRequest = new TechnologyRequest("", "");
        ServerRequest request = MockServerRequest.builder()
                .body(Mono.just(invalidRequest));

        Set<ConstraintViolation<TechnologyRequest>> violations = new HashSet<>();
        ConstraintViolation<TechnologyRequest> nameViolation = Mockito.mock(ConstraintViolation.class);
        Path path = Mockito.mock(Path.class);

        Mockito.when(path.toString()).thenReturn("name");
        Mockito.when(nameViolation.getPropertyPath()).thenReturn(path);
        Mockito.when(nameViolation.getMessage()).thenReturn("is required");
        violations.add(nameViolation);

        Mockito.when(validator.validate(invalidRequest)).thenReturn(violations);

        // Act
        Mono<ServerResponse> responseMono = technologyHandler.createTechnology(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> {
                    return response.statusCode() == HttpStatus.BAD_REQUEST &&
                            response.headers().getContentType().equals(MediaType.APPLICATION_JSON);
                })
                .verifyComplete();

        Mockito.verify(validator).validate(invalidRequest);
        Mockito.verify(technologyRestHandler, Mockito.never()).createTechnology(invalidRequest);
    }

    @Test
    public void test_get_technologies_returns_ok_response() {
        // Arrange
        ServerRequest request = MockServerRequest.builder().build();
        TechnologyModel technologyModel = new TechnologyModel(1L, "Java", "Programming language");
        TechnologyPageModel technologyPageModel = new TechnologyPageModel(Collections.singletonList(technologyModel), 2, 3);

        Mockito.when(technologyRestHandler.getTechnologies(request))
                .thenReturn(Mono.just(technologyPageModel));

        // Act
        Mono<ServerResponse> responseMono = technologyHandler.getTechnologies(request);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.statusCode());
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();
    }
}
