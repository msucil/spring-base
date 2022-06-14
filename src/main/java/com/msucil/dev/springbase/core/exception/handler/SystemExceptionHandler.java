package com.msucil.dev.springbase.core.exception.handler;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.msucil.dev.springbase.core.web.ResponseDetail;

@ControllerAdvice
@Slf4j
public class SystemExceptionHandler extends DefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDetail> beanValidationHandler(MethodArgumentNotValidException ex,
        HttpServletRequest request) {

        log.error("Error occurred while validating request", ex);

        if (ex.getBindingResult() instanceof BeanPropertyBindingResult bindingResult
            && ex.getBindingResult().hasErrors()) {

            final List<FieldError> fieldErrors = new ArrayList<>(
                ex.getBindingResult().getErrorCount());

            for (org.springframework.validation.FieldError error : bindingResult.getFieldErrors()) {
                var fieldError = new FieldError(error.getField(),
                    error.getRejectedValue(),
                    error.getDefaultMessage());

                fieldErrors.add(fieldError);
            }

            return handleError(
                ResponseDetail.builder().title("Validation Failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorCount(fieldErrors.size())
                    .errors(fieldErrors)
                    .message("Request can not be processed due to one or more validation error")
                    .path(request.getPathInfo())
                    .build(),
                HttpStatus.BAD_REQUEST
            );
        }

        return null;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDetail> authenticationErrorHandler(AuthenticationException ex,
        HttpServletRequest request) {

        log.error("Error occurred while authenticating user", ex);

        return handleError(
            ResponseDetail.builder()
                .title("Authentication Failed")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Username/Password is incorrect")
                .path(request.getPathInfo())
                .build()
            , HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDetail> handleDatabaseConstraintViolationException(
        ConstraintViolationException ex, HttpServletRequest request) {

        log.error("Error occurred while interacting with database", ex);

        var errors = ex.getConstraintViolations().stream().map(
            c -> new FieldError(c.getPropertyPath().toString(), c.getInvalidValue(),
                c.getMessage())).collect(Collectors.toList());

        return handleError(ResponseDetail.builder()
                .title("Error Occurred while Saving Record")
                .message("Can not save record due to one or more constraint issues")
                .errorCount(errors.size())
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getPathInfo())
                .build(),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDetail> handleDataIntegrityViolationException(
        DataIntegrityViolationException ex, HttpServletRequest request) {

        log.error("Error occurred while saving records due to data integrity issue {}",
            ex.getLocalizedMessage(), ex);

        return handleError(ResponseDetail.builder()
                .title("Error Occurred while Saving Record")
                .message("Can not save record due to data integrity issue")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getPathInfo())
                .build(),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResponseDetail> handleEmptyResults(
        EmptyResultDataAccessException ex,
        HttpServletRequest request) {

        log.error("Error occurred while processing records {}",
            ex.getLocalizedMessage(), ex);

        return handleError(ResponseDetail.builder()
                .title("Record Does Not Exist")
                .message("The record you are trying to access does not exist")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getPathInfo())
                .build(),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDetail> handleAccessDenied(
        AccessDeniedException ex,
        HttpServletRequest request
    ) {
        log.error("Access denied", ex);

        return handleError(ResponseDetail.builder()
                .title("Access Denied")
                .message("You do not have required privilege to access")
                .status(HttpStatus.FORBIDDEN.value())
                .path(request.getPathInfo())
                .build(),
            HttpStatus.FORBIDDEN
        );
    }
}
