package com.plazoleta.users.commons.configurations.swagger.docs;

import com.plazoleta.users.commons.configurations.swagger.examples.UserSwaggerExamples;
import com.plazoleta.users.users.application.dto.request.SaveUserRequest;
import com.plazoleta.users.users.application.dto.response.SaveUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ClientControllerDocs {
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SwaggerConstants.SUMMARY_CREATE_USER_CLIENT,
            description = SwaggerConstants.DESCRIPTION_CREATE_USER_CLIENT,
            requestBody = @RequestBody(
                    description = SwaggerConstants.DESCRIPTION_CREATE_USER_CLIENT,
                    required = true,
                    content = @Content(
                            mediaType = SwaggerConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = SaveUserRequest.class),
                            examples = @ExampleObject(
                                    name = SwaggerConstants.EXAMPLE_NAME_CREATE_REQUEST,
                                    summary = SwaggerConstants.SUMMARY_CREATE_USER_EXAMPLE,
                                    value = UserSwaggerExamples.CREATE_CLIENT_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = SwaggerConstants.OK,
                    description = SwaggerConstants.DESCRIPTION_CREATE_USER_SUCCESS,
                    content = @Content(
                            mediaType = SwaggerConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = SaveUserResponse.class),
                            examples = @ExampleObject(
                                    name = SwaggerConstants.EXAMPLE_NAME_SUCCESS,
                                    summary = SwaggerConstants.SUMMARY_USER_CREATED,
                                    value = UserSwaggerExamples.CLIENT_CREATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = SwaggerConstants.BAD_REQUEST,
                    description = SwaggerConstants.DESCRIPTION_USER_ALREADY_EXISTS,
                    content = @Content(
                            mediaType = SwaggerConstants.APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SwaggerConstants.EXAMPLE_NAME_VALIDATION_ERROR,
                                    summary = SwaggerConstants.SUMMARY_DUPLICATED_EMAIL,
                                    value = UserSwaggerExamples.USER_ALREADY_EXISTS_RESPONSE
                            )
                    )
            )
    })
    public @interface CreateClientDocs {
    }

}
