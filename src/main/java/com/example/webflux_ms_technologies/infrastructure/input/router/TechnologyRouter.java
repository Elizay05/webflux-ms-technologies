package com.example.webflux_ms_technologies.infrastructure.input.router;

import com.example.webflux_ms_technologies.application.dto.request.TechnologyIdsRequest;
import com.example.webflux_ms_technologies.application.dto.request.TechnologyRequest;
import com.example.webflux_ms_technologies.application.dto.response.TechnologyPageResponse;
import com.example.webflux_ms_technologies.application.dto.response.TechnologyResponse;
import com.example.webflux_ms_technologies.infrastructure.input.handler.TechnologyHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.example.webflux_ms_technologies.infrastructure.input.utils.constants.ConstantsInput.*;

@Configuration
public class TechnologyRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = PATH_TECHNOLOGIES,
                    beanClass = TechnologyHandler.class,
                    beanMethod = METHOD_CREATE,
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = OP_CREATE_TECHNOLOGY,
                            summary = SUMMARY_CREATE_TECHNOLOGY,
                            description = DESC_CREATE_TECHNOLOGY,
                            requestBody = @RequestBody(
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = TechnologyRequest.class),
                                            examples = @ExampleObject(
                                                    name = EXAMPLE_NAME_CREATE,
                                                    value = EXAMPLE_TECHNOLOGY_CREATE
                                            )
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = CODE_201,
                                            description = RESP_TECHNOLOGY_CREATED
                                    ),
                                    @ApiResponse(responseCode = CODE_400,
                                            description = RESP_ERROR_VALIDATION,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    examples = @ExampleObject(
                                                            value = EXAMPLE_ERROR_VALIDATION
                                                    )
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = PATH_TECHNOLOGIES,
                    beanClass = TechnologyHandler.class,
                    beanMethod = METHOD_GET,
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = OP_GET_TECHNOLOGIES,
                            summary = SUMMARY_GET_TECHNOLOGIES,
                            description = DESC_GET_TECHNOLOGIES,
                            parameters = {
                                    @Parameter(name = PARAM_PAGE, description = DESC_PAGE, example = EXAMPLE_PAGE),
                                    @Parameter(name = PARAM_SIZE, description = DESC_SIZE, example = EXAMPLE_SIZE),
                                    @Parameter(name = PARAM_ASC, description = DESC_ASC, example = EXAMPLE_ASC)
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = CODE_200,
                                            description = RESP_TECHNOLOGY_LIST,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = TechnologyPageResponse.class),
                                                    examples = @ExampleObject(
                                                            name = EXAMPLE_NAME_GET,
                                                            value = EXAMPLE_TECHNOLOGY_GET
                                                    )
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = CODE_400,
                                            description = RESP_ERROR_INVALID_PARAMS,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    examples = @ExampleObject(value = EXAMPLE_ERROR_INVALID_PARAMS)
                                            )
                                    ),
                                    @ApiResponse(responseCode = CODE_500, description = RESP_ERROR_INTERNAL_SERVER)
                            }
                    )
            ),
            @RouterOperation(
                    path = PATH_TECHNOLOGIES_BY_IDS,
                    beanClass = TechnologyHandler.class,
                    beanMethod = METHOD_GET_BY_IDS,
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = OP_GET_BY_IDS,
                            summary = SUMMARY_GET_BY_IDS,
                            description = DESC_GET_BY_IDS,
                            requestBody = @RequestBody(
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = TechnologyIdsRequest.class),
                                            examples = @ExampleObject(
                                                    name = EXAMPLE_NAME_GET_BY_IDS,
                                                    value = EXAMPLE_TECHNOLOGY_GET_BY_IDS
                                            )
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = CODE_200,
                                            description = RESP_TECHNOLOGIES_FOUND,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = TechnologyResponse[].class),
                                                    examples = @ExampleObject(
                                                            name = EXAMPLE_NAME_GET_BY_IDS,
                                                            value = EXAMPLE_TECHNOLOGY_GET_BY_IDS_SUCCESS
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = CODE_400,
                                            description = RESP_ERROR_INVALID_BODY,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    examples = @ExampleObject(
                                                            value = EXAMPLE_ERROR_EMPTY_IDS
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = CODE_404,
                                            description = RESP_ERROR_NOT_FOUND,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    examples = @ExampleObject(
                                                            value = EXAMPLE_ERROR_NOT_FOUND
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = CODE_500,
                                            description = RESP_ERROR_INTERNAL_SERVER
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> technologyRoutes(TechnologyHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_TECHNOLOGIES, handler::createTechnology)
                .GET(PATH_TECHNOLOGIES, handler::getTechnologies)
                .POST(PATH_TECHNOLOGIES_BY_IDS, handler::getTechnologiesByIds)
                .build();
    }
}
