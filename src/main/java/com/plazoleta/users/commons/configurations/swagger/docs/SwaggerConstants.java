package com.plazoleta.users.commons.configurations.swagger.docs;

public class SwaggerConstants {

    private SwaggerConstants() {

        throw new IllegalStateException("Utility class");

    }

    public static final String APPLICATION_JSON = "application/json";

    public static final String CREATED = "200";
    public static final String OK = "201";
    public static final String BAD_REQUEST = "400";
    public static final String NOT_FOUND = "404";

    public static final String DESCRIPTION_CREATE_USER_SUCCESS = "Usuario creado exitosamente";
    public static final String DESCRIPTION_USER_ALREADY_EXISTS = "Datos inválidos o usuario ya existente";
    public static final String DESCRIPTION_USER_FOUND = "Usuario encontrado exitosamente";
    public static final String DESCRIPTION_USER_NOT_FOUND = "Usuario no encontrado";

    public static final String SUMMARY_CREATE_USER = "Crear un nuevo usuario en plazoleta";
    public static final String DESCRIPTION_CREATE_USER = "Este endpoint permite registrar un usuario en la plataforma de Plazoleta.";
    public static final String SUMMARY_GET_USER_BY_ID = "Obtener usuario por ID";
    public static final String DESCRIPTION_GET_USER_BY_ID = "Este endpoint permite obtener los datos de un usuario registrado mediante su ID único.";

    public static final String EXAMPLE_NAME_SUCCESS = "Respuesta de éxito";
    public static final String EXAMPLE_NAME_VALIDATION_ERROR = "Error de validación";
    public static final String EXAMPLE_NAME_NOT_FOUND = "Error 404";
    public static final String EXAMPLE_NAME_CREATE_REQUEST = "Ejemplo de solicitud de creación";

    public static final String SUMMARY_USER_CREATED = "Usuario creado";
    public static final String SUMMARY_DUPLICATED_EMAIL = "Email duplicado";
    public static final String SUMMARY_USER_DATA = "Datos del usuario";
    public static final String SUMMARY_USER_NOT_FOUND = "Usuario no encontrado";
    public static final String SUMMARY_CREATE_USER_EXAMPLE = "Creación de usuario";

}
