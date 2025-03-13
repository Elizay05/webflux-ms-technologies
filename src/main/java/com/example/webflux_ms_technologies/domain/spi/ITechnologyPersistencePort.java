package com.example.webflux_ms_technologies.domain.spi;

import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.domain.model.TechnologyPageModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyPersistencePort {
    Mono<Void> saveTechnology(TechnologyModel technologyModel);
    Mono<Boolean> existTechnologyByName(String technologyName);
    Mono<TechnologyPageModel> getAllTechnologies(int page, int size, boolean asc);
    Mono<List<TechnologyModel>> getTechnologiesByIds(List<Long> technologyIds);
}
