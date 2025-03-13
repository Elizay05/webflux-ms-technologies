package com.example.webflux_ms_technologies.domain;

import com.example.webflux_ms_technologies.domain.exceptions.DescriptionTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.NameTooLongException;
import com.example.webflux_ms_technologies.domain.exceptions.TechnologyAlreadyExistsException;
import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.domain.model.TechnologyPageModel;
import com.example.webflux_ms_technologies.domain.spi.ITechnologyPersistencePort;
import com.example.webflux_ms_technologies.domain.usecase.TechnologyUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TechnologyUseCaseTest {

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    @InjectMocks
    private TechnologyUseCase technologyUseCase;


    @Test
    void createTechnologyTest(){
        TechnologyModel newTechnology = new TechnologyModel(1L, "React", "Frontend Framework");

        Mockito.when(technologyPersistencePort.existTechnologyByName(newTechnology.getName())).thenReturn(Mono.just(false));
        Mockito.when(technologyPersistencePort.saveTechnology(newTechnology)).thenReturn(Mono.empty());

        Mono<Void> result = technologyUseCase.createTechnology(newTechnology);

        StepVerifier.create(result).verifyComplete();

        Mockito.verify(technologyPersistencePort, times(1)).existTechnologyByName("React");
        Mockito.verify(technologyPersistencePort, times(1)).saveTechnology(newTechnology);
    }

    @Test
    void createTechnologyTest_shoulThrowAlreadyExistsException(){
        TechnologyModel newTechnology = new TechnologyModel(1L, "React", "Frontend Framework");

        Mockito.when(technologyPersistencePort.existTechnologyByName(newTechnology.getName())).thenReturn(Mono.just(true));

        Mono<Void> result = technologyUseCase.createTechnology(newTechnology);

        StepVerifier.create(result).expectError(TechnologyAlreadyExistsException.class).verify();

        Mockito.verify(technologyPersistencePort, times(1)).existTechnologyByName("React");
        Mockito.verify(technologyPersistencePort, never()).saveTechnology(newTechnology);
    }

    @Test
    void createTechnologyTest_shoulThrowNameTooLongException(){
        String longName = "React".repeat(100);
        TechnologyModel newTechnology = new TechnologyModel(1L, longName, "Frontend Framework");

        Mockito.when(technologyPersistencePort.existTechnologyByName(newTechnology.getName())).thenReturn(Mono.just(false));
        Mono<Void> result = technologyUseCase.createTechnology(newTechnology);

        StepVerifier.create(result).expectError(NameTooLongException.class).verify();

        Mockito.verify(technologyPersistencePort, never()).saveTechnology(Mockito.any());
    }

    @Test
    void createTechnologyTest_shoulThrowDescriptionTooLongException(){
        String longDescription = "Frontend Framework".repeat(100);
        TechnologyModel newTechnology = new TechnologyModel(1L, "React", longDescription);

        Mockito.when(technologyPersistencePort.existTechnologyByName(newTechnology.getName())).thenReturn(Mono.just(false));
        Mono<Void> result = technologyUseCase.createTechnology(newTechnology);

        StepVerifier.create(result).expectError(DescriptionTooLongException.class).verify();

        Mockito.verify(technologyPersistencePort, never()).saveTechnology(Mockito.any());
    }

    @Test
    public void test_get_technologies_returns_paginated_technologies_in_ascending_order() {
        List<TechnologyModel> technologies = Arrays.asList(
                new TechnologyModel(1L, "Java", "Programming language"),
                new TechnologyModel(2L, "Spring", "Framework")
        );
        TechnologyPageModel expectedPage = new TechnologyPageModel(technologies, 1, 2);

        Mockito.when(technologyPersistencePort.getAllTechnologies(0, 10, true))
                .thenReturn(Mono.just(expectedPage));

        // Act
        Mono<TechnologyPageModel> result = technologyUseCase.getTechnologies(0, 10, true);

        // Assert
        StepVerifier.create(result)
                .expectNext(expectedPage)
                .verifyComplete();

        Mockito.verify(technologyPersistencePort).getAllTechnologies(0, 10, true);
    }
}
