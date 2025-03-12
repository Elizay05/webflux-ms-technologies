package com.example.webflux_ms_technologies.infrastructure.output.mysql.mapper;

import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import com.example.webflux_ms_technologies.infrastructure.output.mysql.entity.TechnologyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITechnologyEntityMapper {
    TechnologyEntity toEntity(TechnologyModel model);
    TechnologyModel toModel(TechnologyEntity entity);
}
