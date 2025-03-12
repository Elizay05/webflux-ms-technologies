package com.example.webflux_ms_technologies.application.handler.impl;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.application.handler.ITechnologyRestHandler;
import com.example.webflux_ms_technologies.application.mapper.ITechnologyRequestMapper;
import com.example.webflux_ms_technologies.domain.api.ITechnologyServicePort;
import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyRestHandlerImpl implements ITechnologyRestHandler {

    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyRequestMapper technologyRequestMapper;

    @Override
    public Mono<Void> createTechnology(TechnologyRequest technologyRequest) {
        TechnologyModel technologyModel = technologyRequestMapper.toTechnologyModel(technologyRequest);
        return technologyServicePort.createTechnology(technologyModel);
    }
}
