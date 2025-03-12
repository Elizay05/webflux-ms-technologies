package com.example.webflux_ms_technologies.application.mapper;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITechnologyRequestMapper {
    TechnologyModel toTechnologyModel(TechnologyRequest technologyRequest);
}
