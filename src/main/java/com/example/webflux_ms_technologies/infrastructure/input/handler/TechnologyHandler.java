package com.example.webflux_ms_technologies.infrastructure.input.handler;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyIdsRequest;
import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.application.handler.ITechnologyRestHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.webflux_ms_technologies.infrastructure.input.utils.constants.ConstantsInput.ERROR;
import static com.example.webflux_ms_technologies.infrastructure.input.utils.constants.ConstantsInput.TECHNOLOGY_IDS_EMPTY;

@Component
@RequiredArgsConstructor
public class TechnologyHandler {

    private final ITechnologyRestHandler technologyRestHandler;
    private final Validator validator;

    public Mono<ServerResponse> createTechnology(ServerRequest request) {
        return request.bodyToMono(TechnologyRequest.class)
                .flatMap(techRequest -> {
                    Set<ConstraintViolation<TechnologyRequest>> violations = validator.validate(techRequest);

                    if (!violations.isEmpty()) {
                        String errorMessage = violations.stream()
                                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                                .collect(Collectors.joining(", "));

                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("{" + "\"" + ERROR + "\": \"" + errorMessage + "\"}");
                    }

                    return technologyRestHandler.createTechnology(techRequest)
                            .then(ServerResponse.status(HttpStatus.CREATED).build());
                });
    }

    public Mono<ServerResponse> getTechnologies(ServerRequest request) {
        return technologyRestHandler.getTechnologies(request)
        .flatMap(techPage -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(techPage));
    }

    public Mono<ServerResponse> getTechnologiesByIds(ServerRequest request) {
        return request.bodyToMono(TechnologyIdsRequest.class)
                .flatMap(idsRequest -> {
                    if (idsRequest.getTechnologyIds().isEmpty()) {
                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("{" + "\"" + ERROR + "\": \"" + TECHNOLOGY_IDS_EMPTY + "\"" + "}");
                    }

                    return technologyRestHandler.getTechnologiesByIds(idsRequest.getTechnologyIds())
                            .flatMap(techList -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(techList));
                });
    }
}
