package com.example.webflux_ms_technologies.infrastructure.output.mysql.repository;

import com.example.webflux_ms_technologies.infrastructure.output.mysql.entity.TechnologyEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ITechnologyRepository extends R2dbcRepository<TechnologyEntity, Long> {
    Mono<Boolean> existsTechnologyEntitiesByName(String name);
}
