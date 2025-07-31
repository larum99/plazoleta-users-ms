package com.plazoleta.users.commons.configurations.swagger.examples;

public class UserSwaggerExamples {

    private UserSwaggerExamples() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATE_USER_REQUEST = """
        {
          "firstName": "Carlos",
          "lastName": "Ramírez",
          "identityDocument": "1000123456",
          "phoneNumber": "3123456789",
          "birthDate": "1995-11-20",
          "email": "carlos.ramirez@plazoleta.com",
          "password": "miContraseñaSegura123"
        }
    """;

    public static final String USER_CREATED_RESPONSE = """
        {
          "message": "Usuario creado correctamente.",
          "time": "2025-07-24T12:30:00"
        }
    """;


    public static final String USER_ALREADY_EXISTS_RESPONSE = """
        {
          "message": "El usuario ya existe.",
          "time": "2025-07-24T12:35:00"
        }
    """;

    public static final String USER_FOUND_RESPONSE = """
        {
          "id": 1,
          "firstName": "Carlos",
          "lastName": "Ramírez",
          "identityDocument": "1000123456",
          "phoneNumber": "3123456789",
          "birthDate": "1995-11-20",
          "email": "carlos.ramirez@plazoleta.com",
          "role": "ADMIN"
        }
    """;

    public static final String USER_NOT_FOUND_RESPONSE = """
        {
            "message": "Usuario con ID 99 no encontrado.",
            "time": "2025-07-27T14:45:00"
        }
    """;

    public static final String CREATE_CLIENT_REQUEST = """
        {
             "firstName": "Laura",
             "lastName": "Gómez",
             "identityDocument": "1122334455",
             "phoneNumber": "3001234567",
             "email": "laura.gomez@example.com",
             "password": "clienteSeguro123",
             "birthDate": "1995-04-23"
        }
    """;

    public static final String CLIENT_CREATED_RESPONSE = """
        {
            "id": 5,
            "email": "laura.gomez@example.com",
            "role": "CLIENTE"
        }
    """;

}
