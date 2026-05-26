package com.vehicleregistration.vehicle_registration_system.Config;

import com.vehicleregistration.vehicle_registration_system.Models.BusinessLogicException;
import com.vehicleregistration.vehicle_registration_system.Models.DuplicateResourceException;
import com.vehicleregistration.vehicle_registration_system.Models.NotFoundException;
import com.vehicleregistration.vehicle_registration_system.Models.UnauthorizedException;
import com.vehicleregistration.vehicle_registration_system.Models.ErrorResponse;
import com.vehicleregistration.vehicle_registration_system.Services.Language.ChooseLanguageService;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerConfig {

    private final ChooseLanguageService chooseLanguageService;

    // chooseLanguage.getAcceptedLanguageMessage(exception.getMessage())
    // @Valid failed on a DTO field
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handelDtoException(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errors.put(error.getField(),
                            chooseLanguageService.getAcceptedLanguageMessage(error.getDefaultMessage()));
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        chooseLanguageService.getAcceptedLanguageMessage("error.validation") ,errors));

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(NotFoundException exception) {
        return build(HttpStatus.NOT_FOUND,
                chooseLanguageService.getAcceptedLanguageMessage(exception.getCode() , exception.getArgs()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException exception) {
        return build(HttpStatus.CONFLICT,
                chooseLanguageService.getAcceptedLanguageMessage(exception.getCode() , exception.getArgs()));
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogic(BusinessLogicException exception) {
        return build(HttpStatus.BAD_REQUEST,
                chooseLanguageService.getAcceptedLanguageMessage(exception.getCode() , exception.getArgs()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException exception) {
        return build(HttpStatus.FORBIDDEN, chooseLanguageService.getAcceptedLanguageMessage(exception.getCode() , exception.getArgs()));
    }



    // Spring Security — JWT missing or invalid
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException exception) {
        return build(HttpStatus.UNAUTHORIZED,
                chooseLanguageService.getAcceptedLanguageMessage(exception.getMessage()));
    }

    // Spring Security — user does not have required role
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException exception) {
        return build(HttpStatus.FORBIDDEN,
                chooseLanguageService.getAcceptedLanguageMessage(exception.getMessage()));
    }

    // URL does not exist
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoEndpoint(NoResourceFoundException exception) {
        return build(HttpStatus.NOT_FOUND,
                chooseLanguageService.getAcceptedLanguageMessage(exception.getMessage()));
    }

    // Malformed JSON or missing request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(
            HttpMessageNotReadableException exception) {
        return build(HttpStatus.BAD_REQUEST,
                chooseLanguageService.getAcceptedLanguageMessage(exception.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception) {
        return build(HttpStatus.METHOD_NOT_ALLOWED,
                chooseLanguageService.getAcceptedLanguageMessage("error.method.not.allowed"));
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorResponse> AuthorizationFailed(ServletException exception) {
        return build(HttpStatus.METHOD_NOT_ALLOWED,
                chooseLanguageService.getAcceptedLanguageMessage("error.method.not.allowed"));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> AuthorizationFailed(IOException exception) {
        return build(HttpStatus.METHOD_NOT_ALLOWED,
                chooseLanguageService.getAcceptedLanguageMessage("error.method.not.allowed"));
    }

    // ── Private helper — avoids repeating the same 5 lines ──

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(
                        status.value(),
                        message,
                        null
                ));
    }


}
