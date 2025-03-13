package com.example.webflux_ms_technologies.application.handler.impl;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.application.handler.ITechnologyRestHandler;
import com.example.webflux_ms_technologies.application.mapper.ITechnologyRequestMapper;
import com.example.webflux_ms_technologies.domain.api.ITechnologyServicePort;
import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
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

    @Override
    public Mono<ServerResponse> getTechnologies(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        boolean asc = Boolean.parseBoolean(request.queryParam("asc").orElse("true"));

        return technologyServicePort.getTechnologies(page, size, asc)
                .flatMap(techPage -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(techPage));
    }
}
