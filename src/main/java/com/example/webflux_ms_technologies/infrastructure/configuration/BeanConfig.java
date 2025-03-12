package com.example.webflux_ms_technologies.infrastructure.configuration;

import com.example.webflux_ms_technologies.application.handler.ITechnologyRestHandler;
import com.example.webflux_ms_technologies.application.handler.impl.TechnologyRestHandlerImpl;
import com.example.webflux_ms_technologies.application.mapper.ITechnologyRequestMapper;
import com.example.webflux_ms_technologies.domain.api.ITechnologyServicePort;
import com.example.webflux_ms_technologies.domain.spi.ITechnologyPersistencePort;
import com.example.webflux_ms_technologies.domain.usecase.TechnologyUseCase;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.adapter.TechnologyAdapter;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.mapper.ITechnologyEntityMapper;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.repository.ITechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    private final ITechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;
    private final ITechnologyRequestMapper technologyRequestMapper;

    @Bean
    public ITechnologyServicePort technologyServicePort() {
        return new TechnologyUseCase(technologyPersistencePort());
    }

    @Bean
    public ITechnologyPersistencePort technologyPersistencePort() {
        return new TechnologyAdapter(technologyRepository, technologyEntityMapper);
    }

    @Bean
    public ITechnologyRestHandler technologyRestHandler() {
        return new TechnologyRestHandlerImpl(technologyServicePort(), technologyRequestMapper);
    }
}
