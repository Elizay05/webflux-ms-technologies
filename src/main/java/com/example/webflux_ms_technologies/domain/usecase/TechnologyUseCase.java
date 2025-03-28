package com.example.webflux_ms_technologies.domain.usecase;

import com.example.webflux_ms_technologies.domain.api.ITechnologyServicePort;
import com.example.webflux_ms_technologies.domain.exceptions.DescriptionTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.NameTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.TechnologyAlreadyExistsException;
import com.example.webflux_ms_technologies.domain.exceptions.TechnologyNotFoundException;
import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.domain.model.TechnologyPageModel;
import com.example.webflux_ms_technologies.domain.spi.ITechnologyPersistencePort;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.webflux_ms_technologies.domain.utils.constants.ConstantsDomain.*;

public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort) {
        this.technologyPersistencePort = technologyPersistencePort;
    }

    @Override
    public Mono<Void> createTechnology(TechnologyModel technologyModel) {
        return validateTechnologyAndNameLenght(technologyModel)
                .then(existTechnology(technologyModel.getName()))
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new TechnologyAlreadyExistsException(TECHNOLOGY_NAME_ALREADY_EXISTS));
                    }
                    return technologyPersistencePort.saveTechnology(technologyModel);
                });
    }

    public Mono<Void> validateTechnologyAndNameLenght(TechnologyModel technologyModel) {
        if(technologyModel.getName().length() > MAX_LENGTH_TECHNOLOGY_NAME){
            return Mono.error(new NameTooLongException(String.format(TECHNOLOGY_NAME_TOO_LONG, MAX_LENGTH_TECHNOLOGY_NAME)));
        }
        if(technologyModel.getDescription().length() > MAX_LENGTH_TECHNOLOGY_DESCRIPTION){
            return Mono.error(new DescriptionTooLongException(String.format(TECHNOLOGY_DESCRIPTION_TOO_LONG, MAX_LENGTH_TECHNOLOGY_DESCRIPTION)));
        }
        return Mono.empty();
    }

    public Mono<Boolean> existTechnology(String technologyName) {
        return technologyPersistencePort.existTechnologyByName(technologyName);
    }

    @Override
    public Mono<TechnologyPageModel> getTechnologies(int page, int size, boolean asc) {
        return technologyPersistencePort.getAllTechnologies(page, size, asc);
    }

    @Override
    public Mono<List<TechnologyModel>> getTechnologiesByIds(List<Long> technologyIds) {
        return technologyPersistencePort.getTechnologiesByIds(technologyIds)
                .flatMap(techList -> {
                    if (techList.isEmpty()) {
                        return Mono.error(new TechnologyNotFoundException(TECHNOLOGIES_NOT_FOUND));
                    }
                    return Mono.just(techList);
                });
    }
}
