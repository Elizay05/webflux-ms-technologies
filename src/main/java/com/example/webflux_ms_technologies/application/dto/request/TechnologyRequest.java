package com.example.webflux_ms_technologies.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static com.example.webflux_ms_technologies.application.utils.constants.ConstantsApplication.IS_REQUIRED;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechnologyRequest {

    @NotBlank(message = IS_REQUIRED)
    private String name;

    @NotBlank(message = IS_REQUIRED)
    private String description;
}
