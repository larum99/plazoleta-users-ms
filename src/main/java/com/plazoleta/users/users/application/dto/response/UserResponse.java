package com.plazoleta.users.users.application.dto.response;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String identityDocument,
        String phoneNumber,
        LocalDate birthDate,
        String email,
        String role
) {
}
