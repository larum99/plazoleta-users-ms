package com.plazoleta.users.users.infrastructure.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtClaimsModel {
    private Long id;
    private String role;
}
