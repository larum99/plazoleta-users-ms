package com.plazoleta.users.commons.configurations.swagger.docs;

import com.plazoleta.users.commons.configurations.swagger.examples.AuthenticationSwaggerExamples;
import com.plazoleta.users.users.application.dto.request.AuthenticationRequest;
import com.plazoleta.users.users.application.dto.response.AuthenticationResponse;
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

import static com.plazoleta.users.commons.configurations.swagger.docs.SwaggerConstants.*;

public class AuthControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_LOGIN,
            description = DESCRIPTION_LOGIN,
            requestBody = @RequestBody(
                    description = DESCRIPTION_CREDENTIALS,
                    required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = AuthenticationRequest.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_REQUEST,
                                    summary = SUMMARY_REQUEST_EXAMPLE,
                                    description = DESCRIPTION_REQUEST_EXAMPLE,
                                    value = AuthenticationSwaggerExamples.AUTHENTICATION_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = RESPONSE_SUCCESS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = AuthenticationResponse.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_SUCCESS_TOKEN,
                                    summary = SUMMARY_RESPONSE_EXAMPLE,
                                    description = DESCRIPTION_RESPONSE_EXAMPLE,
                                    value = AuthenticationSwaggerExamples.AUTHENTICATION_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = UNAUTHORIZED,
                    description = RESPONSE_ERROR,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_ERROR,
                                    summary = SUMMARY_ERROR_EXAMPLE,
                                    description = DESCRIPTION_ERROR_EXAMPLE,
                                    value = AuthenticationSwaggerExamples.AUTHENTICATION_ERROR_RESPONSE
                            )
                    )
            )
    })
    public @interface LoginDocs {
    }
}
