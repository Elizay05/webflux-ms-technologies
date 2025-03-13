package com.example.webflux_ms_technologies.application.handler.impl;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.application.dto.response.TechnologyPageResponse;
import com.example.webflux_ms_technologies.application.dto.response.TechnologyResponse;
import com.example.webflux_ms_technologies.application.handler.ITechnologyRestHandler;
import com.example.webflux_ms_technologies.application.mapper.ITechnologyPageResponseMapper;
import com.example.webflux_ms_technologies.application.mapper.ITechnologyRequestMapper;
import com.example.webflux_ms_technologies.application.mapper.ITechnologyResponseMapper;
import com.example.webflux_ms_technologies.domain.api.ITechnologyServicePort;
import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TechnologyRestHandlerImpl implements ITechnologyRestHandler {

    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyRequestMapper technologyRequestMapper;
    private final ITechnologyResponseMapper technologyResponseMapper;
    private final ITechnologyPageResponseMapper technologyPageResponseMapper;

    @Override
    public Mono<Void> createTechnology(TechnologyRequest technologyRequest) {
        TechnologyModel technologyModel = technologyRequestMapper.toTechnologyModel(technologyRequest);
        return technologyServicePort.createTechnology(technologyModel);
    }

    @Override
    public Mono<TechnologyPageResponse> getTechnologies(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        boolean asc = Boolean.parseBoolean(request.queryParam("asc").orElse("true"));

        return technologyServicePort.getTechnologies(page, size, asc)
                .map(technologyPageResponseMapper::toTechnologyPageResponse);
    }

    @Override
    public Mono<List<TechnologyResponse>> getTechnologiesByIds(List<Long> technologyIds) {
        return technologyServicePort.getTechnologiesByIds(technologyIds)
                .map(technologyResponseMapper::toListTechnologyResponse);
    }
}
