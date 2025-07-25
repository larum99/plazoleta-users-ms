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

public class UserControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Crear un nuevo usuario en plazoleta",
            description = "Este endpoint permite registrar un usuario en la plataforma de Plazoleta.",
            requestBody = @RequestBody(
                    description = "Datos necesarios para crear un usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveUserRequest.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de solicitud de creación",
                                    summary = "Creación de usuario",
                                    value = UserSwaggerExamples.CREATE_USER_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveUserResponse.class),
                            examples = @ExampleObject(
                                    name = "Respuesta de éxito",
                                    summary = "Usuario creado",
                                    value = UserSwaggerExamples.USER_CREATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Error de validación",
                                    summary = "Email duplicado",
                                    value = UserSwaggerExamples.USER_ALREADY_EXISTS_RESPONSE
                            )
                    )
            )
    })
    public @interface CreateUserDocs {
    }
}
