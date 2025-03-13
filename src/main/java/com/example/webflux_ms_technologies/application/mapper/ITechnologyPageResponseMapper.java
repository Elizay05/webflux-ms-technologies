package com.example.webflux_ms_technologies.application.mapper;

import com.example.webflux_ms_technologies.application.dto.response.TechnologyPageResponse;
import com.example.webflux_ms_technologies.domain.model.TechnologyPageModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITechnologyPageResponseMapper {

    TechnologyPageResponse toTechnologyPageResponse(TechnologyPageModel model);
}
