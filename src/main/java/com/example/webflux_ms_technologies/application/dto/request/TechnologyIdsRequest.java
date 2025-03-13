package com.example.webflux_ms_technologies.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechnologyIdsRequest {
    private List<Long> technologyIds;
}
