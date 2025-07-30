package com.plazoleta.users.commons.configurations.swagger.examples;

public class AuthenticationSwaggerExamples {

    private AuthenticationSwaggerExamples() {
        throw new IllegalStateException("Utility class");
    }

    public static final String AUTHENTICATION_REQUEST = """
    {
       "email": "jenny@gmail.com",
       "password": "miContraseñaSegura123"
    }
""";

    public static final String AUTHENTICATION_RESPONSE = """
    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
""";

    public static final String AUTHENTICATION_ERROR_RESPONSE = """
    {
      "message": "Invalid email or password.",
      "time": "2025-04-07T12:10:00"
    }
""";

}
