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
    public static final String UNAUTHORIZED = "401";

    public static final String DESCRIPTION_CREATE_USER_SUCCESS = "Usuario creado exitosamente";
    public static final String DESCRIPTION_USER_ALREADY_EXISTS = "Datos inválidos o usuario ya existente";
    public static final String DESCRIPTION_USER_FOUND = "Usuario encontrado exitosamente";
    public static final String DESCRIPTION_USER_NOT_FOUND = "Usuario no encontrado";

    public static final String SUMMARY_CREATE_USER_OWNER = "Crear un usuario propietario en plazoleta";
    public static final String DESCRIPTION_CREATE_USER_OWNER = "Este endpoint permite registrar un usuario propietario en la plataforma de Plazoleta.";
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

    public static final String SUMMARY_LOGIN = "Autenticación de usuarios";
    public static final String DESCRIPTION_LOGIN = "Permite iniciar sesión a un usuario registrado para obtener su token JWT.";
    public static final String DESCRIPTION_CREDENTIALS = "Credenciales del usuario";

    public static final String RESPONSE_SUCCESS = "Login exitoso";
    public static final String RESPONSE_ERROR = "Credenciales inválidas";

    public static final String EXAMPLE_NAME_REQUEST = "Ejemplo de login";
    public static final String EXAMPLE_NAME_SUCCESS_TOKEN = "Token generado";
    public static final String EXAMPLE_NAME_ERROR = "Error de autenticación";

    public static final String SUMMARY_REQUEST_EXAMPLE = "Login exitoso";
    public static final String SUMMARY_RESPONSE_EXAMPLE = "Respuesta de login";
    public static final String SUMMARY_ERROR_EXAMPLE = "Login fallido";

    public static final String DESCRIPTION_REQUEST_EXAMPLE = "Petición de login enviando correo y contraseña";
    public static final String DESCRIPTION_RESPONSE_EXAMPLE = "Token JWT generado tras autenticarse";
    public static final String DESCRIPTION_ERROR_EXAMPLE = "Credenciales incorrectas";

    public static final String SUMMARY_CREATE_EMPLOYEE = "Crear un empleado en plazoleta";
    public static final String DESCRIPTION_CREATE_EMPLOYEE = "Este endpoint permite al propietario registrar un nuevo empleado.";
    public static final String SUMMARY_CREATE_EMPLOYEE_EXAMPLE = "Ejemplo de creación de empleado";
    public static final String SUMMARY_EMPLOYEE_CREATED = "Empleado creado exitosamente";
    public static final String DESCRIPTION_CREATE_EMPLOYEE_SUCCESS = "Empleado creado correctamente en la base de datos.";
    public static final String SUMMARY_CREATE_USER_CLIENT = "Crear cuenta de cliente";
    public static final String DESCRIPTION_CREATE_USER_CLIENT = "Permite registrar un nuevo cliente con los datos obligatorios";


}
