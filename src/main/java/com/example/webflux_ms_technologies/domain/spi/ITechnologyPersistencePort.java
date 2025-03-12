package com.example.webflux_ms_technologies.domain.spi;

import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import reactor.core.publisher.Mono;

public interface ITechnologyPersistencePort {
    Mono<Void> saveTechnology(TechnologyModel technologyModel);
    Mono<Boolean> existTechnologyByName(String technologyName);
}
