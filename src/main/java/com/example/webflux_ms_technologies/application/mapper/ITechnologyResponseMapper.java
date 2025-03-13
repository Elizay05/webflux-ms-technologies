package com.example.webflux_ms_technologies.application.mapper;

import com.example.webflux_ms_technologies.application.dto.response.TechnologyResponse;
import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITechnologyResponseMapper {
    List<TechnologyResponse> toListTechnologyResponse(List<TechnologyModel> model);
}
