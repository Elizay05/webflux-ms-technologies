package com.example.webflux_ms_technologies.application.handler;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ITechnologyRestHandler {
    Mono<Void> createTechnology(TechnologyRequest technologyRequest);
    Mono<ServerResponse> getTechnologies(ServerRequest request);
}
