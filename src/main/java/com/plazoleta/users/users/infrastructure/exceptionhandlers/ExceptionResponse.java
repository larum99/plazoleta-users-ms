package com.plazoleta.users.users.infrastructure.exceptionhandlers;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime timeStamp) {
}

