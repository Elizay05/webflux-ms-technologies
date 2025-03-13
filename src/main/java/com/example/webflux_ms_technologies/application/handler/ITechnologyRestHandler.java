package com.example.webflux_ms_technologies.application.handler;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.domain.model.TechnologyPageModel;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface ITechnologyRestHandler {
    Mono<Void> createTechnology(TechnologyRequest technologyRequest);
    Mono<TechnologyPageModel> getTechnologies(ServerRequest request);
}
