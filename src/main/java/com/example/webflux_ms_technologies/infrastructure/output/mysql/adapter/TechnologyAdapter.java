package com.example.webflux_ms_technologies.infrastructure.output.mysql.adapter;

import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.domain.model.TechnologyPageModel;
import com.example.webflux_ms_technologies.domain.spi.ITechnologyPersistencePort;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.entity.TechnologyEntity;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.exceptions.SomeTechnologiesNotFoundException;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.mapper.ITechnologyEntityMapper;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.repository.ITechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.webflux_ms_technologies.infrastructure.output.mysql.util.constants.ConstantsOutput.SOME_TECHNOLOGIES_NOT_FOUND;

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

    @Override
    public Mono<TechnologyPageModel> getAllTechnologies(int page, int size, boolean asc) {
        Pageable pageable = PageRequest.of(page, size);

        Flux<TechnologyEntity> techFlux = asc ?
                technologyRepository.findAllByOrderByNameAsc(pageable) :
                technologyRepository.findAllByOrderByNameDesc(pageable);

        return technologyRepository.count()
                .zipWith(techFlux.map(technologyEntityMapper::toModel).collectList())
                .map(tuple -> {
                    long totalItems = tuple.getT1();
                    List<TechnologyModel> technologies = tuple.getT2();
                    int totalPages = (int) Math.ceil((double) totalItems / size);
                    return new TechnologyPageModel(technologies, totalPages, totalItems);
                });
    }

    @Override
    public Mono<List<TechnologyModel>> getTechnologiesByIds(List<Long> technologyIds) {
        return technologyRepository.findAllById(technologyIds)
                .map(technologyEntityMapper::toModel)
                .collectList()
                .flatMap(techList -> {
                    if (techList.size() < technologyIds.size()) {
                        return Mono.error(new SomeTechnologiesNotFoundException(SOME_TECHNOLOGIES_NOT_FOUND));
                    }
                    return Mono.just(techList);
                });
    }
}
