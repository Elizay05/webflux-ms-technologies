package com.example.webflux_ms_technologies.application.dto.response;

import com.example.webflux_ms_technologies.domain.model.TechnologyModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechnologyPageResponse {
    private List<TechnologyModel> technologies;
    private int totalPages;
    private long totalElements;
}
