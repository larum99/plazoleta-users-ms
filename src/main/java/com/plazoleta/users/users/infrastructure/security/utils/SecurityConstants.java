package com.plazoleta.users.users.infrastructure.security.utils;

import java.security.PublicKey;
import java.util.List;

public class SecurityConstants {

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ID_CLAIM = "id";
    public static final String ROLE_CLAIM = "role";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";


    public static final String AUTH_PATH = "/api/v1/users/auth/**";
    public static final String SWAGGER_UI_PATH = "/swagger-ui/**";
    public static final String SWAGGER_UI_HTML_PATH = "/swagger-ui.html";
    public static final String API_DOCS_PATH = "/v3/api-docs/**";
    public static final String API_DOCS_PATH_NO_SLASH = "/v3/api-docs";

    public static final List<String> ALLOWED_ORIGINS = List.of(
            "http://localhost:8090"
    );

    public static final List<String> ALLOWED_METHODS = List.of("GET", "POST", "PUT", "DELETE");
    public static final List<String> ALLOWED_HEADERS = List.of("*");

    public static final String APPLICATION_JSON = "application/json";

    public static final String ACCESS_DENIED_MESSAGE_TEMPLATE = """
        {
          "message": "Acceso denegado: no tienes los permisos necesarios.",
          "timestamp": "%s"
        }
        """;
}
