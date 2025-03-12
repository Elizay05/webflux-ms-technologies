package com.example.webflux_ms_technologies.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechnologyRequest {

    @NotBlank(message = "is required")
    private String name;

    @NotBlank(message = "is required")
    private String description;
}
