package com.example.webflux_ms_technologies.infrastructure.input.router;

import com.example.webflux_ms_technologies.infrastructure.input.handler.TechnologyHandler;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.example.webflux_ms_technologies.infrastructure.input.utils.constants.ConstantsInput.PATH_TECHNOLOGIES;
import static com.example.webflux_ms_technologies.infrastructure.input.utils.constants.ConstantsInput.PATH_TECHNOLOGIES_BY_IDS;

@Configuration
public class TechnologyRouter {

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(Configuration.class);
    }

    @Bean
    public RouterFunction<ServerResponse> technologyRoutes(TechnologyHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_TECHNOLOGIES, handler::createTechnology)
                .GET(PATH_TECHNOLOGIES, handler::getTechnologies)
                .POST(PATH_TECHNOLOGIES_BY_IDS, handler::getTechnologiesByIds)
                .build();
    }
}
