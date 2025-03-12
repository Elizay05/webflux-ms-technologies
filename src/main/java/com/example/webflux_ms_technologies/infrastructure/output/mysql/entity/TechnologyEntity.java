package com.example.webflux_ms_technologies.infrastructure.output.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("technology")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TechnologyEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
