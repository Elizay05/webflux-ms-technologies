package com.example.webflux_ms_technologies.infrastructure.output.mysql.adapter;

import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.domain.model.TechnologyPageModel;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.entity.TechnologyEntity;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.exceptions.SomeTechnologiesNotFoundException;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.mapper.ITechnologyEntityMapper;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.repository.ITechnologyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static com.example.webflux_ms_technologies.infrastructure.output.mysql.util.constants.ConstantsOutput.SOME_TECHNOLOGIES_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        when(technologyEntityMapper.toEntity(technologyModel)).thenReturn(technologyEntity);
        when(technologyRepository.save(technologyEntity)).thenReturn(Mono.just(technologyEntity));

        // Act
        Mono<Void> result = technologyAdapter.saveTechnology(technologyModel);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(technologyEntityMapper).toEntity(technologyModel);
        verify(technologyRepository).save(technologyEntity);
    }

    @Test
    public void test_exist_technology_by_name_returns_true_when_technology_exists() {
        String technologyName = "Java";

        when(technologyRepository.existsTechnologyEntitiesByName(technologyName))
                .thenReturn(Mono.just(true));

        // Act
        Mono<Boolean> result = technologyAdapter.existTechnologyByName(technologyName);

        // Assert
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(technologyRepository).existsTechnologyEntitiesByName(technologyName);
    }

    @Test
    public void test_exist_technology_by_name_returns_false_when_technology_does_not_exist() {
        String technologyName = "NonExistentTech";

        when(technologyRepository.existsTechnologyEntitiesByName(technologyName))
                .thenReturn(Mono.just(false));

        // Act
        Mono<Boolean> result = technologyAdapter.existTechnologyByName(technologyName);

        // Assert
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();

        verify(technologyRepository).existsTechnologyEntitiesByName(technologyName);
    }

    @Test
    public void test_get_all_technologies_returns_technology_page_model_with_data_asc() {
        int page = 0;
        int size = 10;
        boolean asc = true;

        TechnologyEntity entity1 = new TechnologyEntity(1L, "Java", "Programming language");
        TechnologyEntity entity2 = new TechnologyEntity(2L, "Spring", "Framework");

        TechnologyModel model1 = new TechnologyModel(1L, "Java", "Programming language");
        TechnologyModel model2 = new TechnologyModel(2L, "Spring", "Framework");

        Mockito.when(technologyRepository.findAllByOrderByNameAsc(any(Pageable.class)))
                .thenReturn(Flux.just(entity1, entity2));
        Mockito.when(technologyRepository.count()).thenReturn(Mono.just(2L));
        Mockito.when(technologyEntityMapper.toModel(entity1)).thenReturn(model1);
        Mockito.when(technologyEntityMapper.toModel(entity2)).thenReturn(model2);

        // Act
        Mono<TechnologyPageModel> result = technologyAdapter.getAllTechnologies(page, size, asc);

        // Assert
        StepVerifier.create(result)
                .assertNext(pageModel -> {
                    assertEquals(2, pageModel.getTechnologies().size());
                    assertEquals(1, pageModel.getTotalPages());
                    assertEquals(2L, pageModel.getTotalElements());
                    assertEquals("Java", pageModel.getTechnologies().get(0).getName());
                    assertEquals("Spring", pageModel.getTechnologies().get(1).getName());
                })
                .verifyComplete();
    }

    @Test
    public void test_get_all_technologies_returns_technology_page_model_with_data_desc() {
        int page = 0;
        int size = 10;
        boolean asc = false;

        TechnologyEntity entity1 = new TechnologyEntity(1L, "Java", "Programming language");
        TechnologyEntity entity2 = new TechnologyEntity(2L, "Spring", "Framework");

        TechnologyModel model1 = new TechnologyModel(1L, "Java", "Programming language");
        TechnologyModel model2 = new TechnologyModel(2L, "Spring", "Framework");

        Mockito.when(technologyRepository.findAllByOrderByNameDesc(any(Pageable.class)))
                .thenReturn(Flux.just(entity2, entity1)); // En orden descendente
        Mockito.when(technologyRepository.count()).thenReturn(Mono.just(2L));
        Mockito.when(technologyEntityMapper.toModel(entity1)).thenReturn(model1);
        Mockito.when(technologyEntityMapper.toModel(entity2)).thenReturn(model2);

        // Act
        Mono<TechnologyPageModel> result = technologyAdapter.getAllTechnologies(page, size, asc);

        // Assert
        StepVerifier.create(result)
                .assertNext(pageModel -> {
                    assertEquals(2, pageModel.getTechnologies().size());
                    assertEquals(1, pageModel.getTotalPages());
                    assertEquals(2L, pageModel.getTotalElements());
                    assertEquals("Spring", pageModel.getTechnologies().get(0).getName()); // Ahora "Spring" es el primero
                    assertEquals("Java", pageModel.getTechnologies().get(1).getName()); // Ahora "Java" es el segundo
                })
                .verifyComplete();
    }

    @Test
    public void test_get_all_technologies_handles_empty_repository() {
        int page = 0;
        int size = 10;
        boolean asc = true;

        Mockito.when(technologyRepository.findAllByOrderByNameAsc(any(Pageable.class)))
                .thenReturn(Flux.empty());
        Mockito.when(technologyRepository.count()).thenReturn(Mono.just(0L));

        // Act
        Mono<TechnologyPageModel> result = technologyAdapter.getAllTechnologies(page, size, asc);

        // Assert
        StepVerifier.create(result)
                .assertNext(pageModel -> {
                    assertNotNull(pageModel);
                    assertTrue(pageModel.getTechnologies().isEmpty());
                    assertEquals(0, pageModel.getTotalPages());
                    assertEquals(0L, pageModel.getTotalElements());
                })
                .verifyComplete();
    }

    @Test
    public void test_getTechnologiesByIds_success() {
        // Arrange
        List<Long> technologyIds = List.of(1L, 2L);

        TechnologyEntity entity1 = new TechnologyEntity(1L, "Java", "Programming language");
        TechnologyEntity entity2 = new TechnologyEntity(2L, "Spring", "Framework");

        TechnologyModel model1 = new TechnologyModel(1L, "Java", "Programming language");
        TechnologyModel model2 = new TechnologyModel(2L, "Spring", "Framework");

        when(technologyRepository.findAllById(technologyIds)).thenReturn(Flux.just(entity1, entity2));
        when(technologyEntityMapper.toModel(entity1)).thenReturn(model1);
        when(technologyEntityMapper.toModel(entity2)).thenReturn(model2);

        // Act
        Mono<List<TechnologyModel>> result = technologyAdapter.getTechnologiesByIds(technologyIds);

        // Assert
        StepVerifier.create(result)
                .assertNext(techList -> {
                    assertEquals(2, techList.size());
                    assertEquals("Java", techList.get(0).getName());
                    assertEquals("Spring", techList.get(1).getName());
                })
                .verifyComplete();

        verify(technologyRepository).findAllById(technologyIds);
        verify(technologyEntityMapper).toModel(entity1);
        verify(technologyEntityMapper).toModel(entity2);
    }

    @Test
    public void test_getTechnologiesByIds_someTechnologiesNotFound() {
        List<Long> technologyIds = List.of(1L, 2L, 3L); // Buscamos 3 tecnologías, pero solo existen 2

        TechnologyEntity entity1 = new TechnologyEntity(1L, "Java", "Programming language");
        TechnologyEntity entity2 = new TechnologyEntity(2L, "Spring", "Framework");

        TechnologyModel model1 = new TechnologyModel(1L, "Java", "Programming language");
        TechnologyModel model2 = new TechnologyModel(2L, "Spring", "Framework");

        when(technologyRepository.findAllById(technologyIds)).thenReturn(Flux.just(entity1, entity2));
        when(technologyEntityMapper.toModel(entity1)).thenReturn(model1);
        when(technologyEntityMapper.toModel(entity2)).thenReturn(model2);

        // Act
        Mono<List<TechnologyModel>> result = technologyAdapter.getTechnologiesByIds(technologyIds);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof SomeTechnologiesNotFoundException &&
                                throwable.getMessage().equals(SOME_TECHNOLOGIES_NOT_FOUND))
                .verify();

        verify(technologyRepository).findAllById(technologyIds);
        verify(technologyEntityMapper).toModel(entity1);
        verify(technologyEntityMapper).toModel(entity2);
    }
}
