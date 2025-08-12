package com.plazoleta.users.users.infrastructure.utils.constants;

public class FeignConstants {

    private FeignConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_SERVICE = "plazoleta-service";
    public static final String URL_SERVICE = "${microservices.plazoleta.url}";

    public static final String CREATE_EMPLOYEE_PATH = "/api/v1/plazoleta/employee-restaurant";
}
