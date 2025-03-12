package com.example.webflux_ms_technologies.application.handler;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import reactor.core.publisher.Mono;

public interface ITechnologyRestHandler {
    Mono<Void> createTechnology(TechnologyRequest technologyRequest);
}
