package com.plazoleta.users.users.infrastructure.exceptionhandlers;

import com.plazoleta.users.users.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidEmail(InvalidEmailException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_EMAIL_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidPhoneException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidPhone(InvalidPhoneException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_PHONE_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidDocumentException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidDocument(InvalidDocumentException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_DOCUMENT_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(UnderAgeUserException.class)
    public ResponseEntity<ExceptionResponse> handleUnderAge(UnderAgeUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                ExceptionConstants.UNDERAGE_USER_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(
                ExceptionConstants.USER_ALREADY_EMAIL_EXISTS_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(DuplicateDocumentException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateDocument(DuplicateDocumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(
                ExceptionConstants.USER_ALREADY_DOCUMENTID_EXISTS_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(MissingFieldException.class)
    public ResponseEntity<ExceptionResponse> handleMissingField(MissingFieldException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRole(InvalidRoleException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_ROLE_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ex.getMessage() != null ? ex.getMessage() : ExceptionConstants.USER_NOT_FOUND_MESSAGE,
                LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(
                ExceptionConstants.INVALID_CREDENTIALS_MESSAGE, LocalDateTime.now()));
    }
}
