package com.plazoleta.users.users.infrastructure.exceptionhandlers;

public class ExceptionConstants {

    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String INVALID_EMAIL_MESSAGE = "La dirección de correo electrónico no es válida.";
    public static final String INVALID_PHONE_MESSAGE = "El número de teléfono no es válido.";
    public static final String INVALID_DOCUMENT_MESSAGE = "El documento de identidad no es válido. Debe contener solo números.";
    public static final String UNDERAGE_USER_MESSAGE = "El usuario es menor de edad.";
    public static final String USER_ALREADY_EMAIL_EXISTS_MESSAGE = "Ya existe un usuario con este correo electrónico.";
    public static final String USER_ALREADY_DOCUMENTID_EXISTS_MESSAGE = "Ya existe un usuario con este documento.";
    public static final String INVALID_ROLE_MESSAGE = "El rol proporcionado no es válido.";
    public static final String USER_NOT_FOUND_MESSAGE = "El usuario con el ID proporcionado no fue encontrado.";
}

