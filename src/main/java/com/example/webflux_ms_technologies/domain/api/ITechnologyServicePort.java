package com.example.webflux_ms_technologies.domain.api;

import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.domain.model.TechnologyPageModel;
import reactor.core.publisher.Mono;

public interface ITechnologyServicePort {
    Mono<Void> createTechnology(TechnologyModel technologyModel);
    Mono<TechnologyPageModel> getTechnologies(int page, int size, boolean asc);
}
