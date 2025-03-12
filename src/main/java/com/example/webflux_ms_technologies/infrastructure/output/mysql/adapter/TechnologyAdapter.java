package com.example.webflux_ms_technologies.infrastructure.output.mysql.adapter;

import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.domain.spi.ITechnologyPersistencePort;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.entity.TechnologyEntity;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.mapper.ITechnologyEntityMapper;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.repository.ITechnologyRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyAdapter implements ITechnologyPersistencePort {

    private final ITechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;

    @Override
    public Mono<Void> saveTechnology(TechnologyModel technologyModel) {
        TechnologyEntity entity = technologyEntityMapper.toEntity(technologyModel);
        return technologyRepository.save(entity).then();
    }

    @Override
    public Mono<Boolean> existTechnologyByName(String technologyName) {
        return technologyRepository.existsTechnologyEntitiesByName(technologyName);
    }
}
