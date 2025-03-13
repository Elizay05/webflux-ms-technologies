package com.example.webflux_ms_technologies.application.handler;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.application.dto.response.TechnologyPageResponse;
import com.example.webflux_ms_technologies.application.dto.response.TechnologyResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyRestHandler {
    Mono<Void> createTechnology(TechnologyRequest technologyRequest);
    Mono<TechnologyPageResponse> getTechnologies(ServerRequest request);
    Mono<List<TechnologyResponse>> getTechnologiesByIds(List<Long> technologyIds);
}
