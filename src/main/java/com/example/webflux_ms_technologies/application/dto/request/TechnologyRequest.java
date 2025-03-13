package com.example.webflux_ms_technologies.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechnologyRequest {

    @NotBlank(message = "es requerido")
    private String name;

    @NotBlank(message = "es requerido")
    private String description;
}
