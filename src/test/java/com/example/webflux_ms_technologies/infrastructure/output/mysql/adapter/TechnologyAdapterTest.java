package com.example.webflux_ms_technologies.infrastructure.output.mysql.adapter;

import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.entity.TechnologyEntity;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.mapper.ITechnologyEntityMapper;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.repository.ITechnologyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class TechnologyAdapterTest {

    @Mock
    private ITechnologyRepository technologyRepository;

    @Mock
    private ITechnologyEntityMapper technologyEntityMapper;

    @InjectMocks
    private TechnologyAdapter technologyAdapter;

    @Test
    public void test_save_technology_successfully() {
        TechnologyModel technologyModel = new TechnologyModel(1L, "Java", "Programming language");
        TechnologyEntity technologyEntity = new TechnologyEntity(1L, "Java", "Programming language");

        Mockito.when(technologyEntityMapper.toEntity(technologyModel)).thenReturn(technologyEntity);
        Mockito.when(technologyRepository.save(technologyEntity)).thenReturn(Mono.just(technologyEntity));

        // Act
        Mono<Void> result = technologyAdapter.saveTechnology(technologyModel);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        Mockito.verify(technologyEntityMapper).toEntity(technologyModel);
        Mockito.verify(technologyRepository).save(technologyEntity);
    }
}
