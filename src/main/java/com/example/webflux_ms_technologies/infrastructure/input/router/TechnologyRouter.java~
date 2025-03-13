package com.example.webflux_ms_technologies.infrastructure.input.router;

import com.example.webflux_ms_technologies.infrastructure.input.handler.TechnologyHandler;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TechnologyRouter {

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(Configuration.class);
    }

    @Bean
    public RouterFunction<ServerResponse> technologyRoutes(TechnologyHandler handler) {
        return RouterFunctions.route()
                .POST("/technologies", handler::createTechnology)
                .build();
    }
}
