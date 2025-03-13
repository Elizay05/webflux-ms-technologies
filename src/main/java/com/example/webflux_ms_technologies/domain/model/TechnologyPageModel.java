package com.example.webflux_ms_technologies.domain.model;

import java.util.List;

public class TechnologyPageModel {
    private List<TechnologyModel> technologies;
    private int totalPages;
    private long totalElements;

    public TechnologyPageModel(List<TechnologyModel> technologies, int totalPages, long totalElements) {
        this.technologies = technologies;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<TechnologyModel> getTechnologies() { return technologies; }
    public int getTotalPages() { return totalPages; }
    public long getTotalElements() { return totalElements; }
}
