package com.example.webflux_ms_technologies.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechnologyResponse {
    private Long id;
    private String name;
    private String description;
}
