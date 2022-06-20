package com.msucil.dev.springbase.core.exception.handler;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.msucil.dev.springbase.core.web.ResponseDetail;

@ControllerAdvice
@Slf4j
public class ValidationExceptionHandler extends DefaultExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseDetail> beanValidationHandler(BindException ex,
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
}
