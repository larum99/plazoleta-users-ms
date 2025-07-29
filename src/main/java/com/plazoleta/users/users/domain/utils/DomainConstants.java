package com.plazoleta.users.users.domain.utils;

public class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final int PHONE_MAX_LENGTH = 13;
    public static final int MIN_AGE = 18;

    public static final String ERROR_REQUIRED_FIRSTNAME = "El nombre es obligatorio.";
    public static final String ERROR_REQUIRED_LASTNAME = "El apellido es obligatorio.";
    public static final String ERROR_REQUIRED_DOCUMENT = "El documento es obligatorio.";
    public static final String ERROR_REQUIRED_PHONE = "El número de teléfono es obligatorio.";
    public static final String ERROR_REQUIRED_BIRTHDATE = "La fecha de nacimiento es obligatoria.";
    public static final String ERROR_REQUIRED_EMAIL = "El correo electrónico es obligatorio.";
    public static final String ERROR_REQUIRED_PASSWORD = "La contraseña es obligatoria.";
    public static final String ERROR_REQUIRED_ROLE_ID = "El rol es obligatorio.";
}
