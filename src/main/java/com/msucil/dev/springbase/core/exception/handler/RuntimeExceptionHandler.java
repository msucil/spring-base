package com.msucil.dev.springbase.core.exception.handler;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.msucil.dev.springbase.core.web.ResponseDetail;

@ControllerAdvice
@Slf4j
public class RuntimeExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseDetail> runTimeExceptionHandler(ResponseStatusException ex,
        HttpServletRequest request) {

        log.error("Http Exception", ex);

        return ResponseEntity
            .status(ex.getStatus())
            .body(
                ResponseDetail.builder().status(ex.getStatus().value())
                    .path(request.getPathInfo()).message(ex.getReason())
                    .build()
            );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDetail> runTimeExceptionHandler(RuntimeException ex,
        HttpServletRequest request) {

        log.error("Runtime exception", ex);

        return ResponseEntity.badRequest().body(
            ResponseDetail.builder().status(HttpStatus.BAD_REQUEST.value())
                .path(request.getPathInfo()).message(ex.getLocalizedMessage()).build()
        );
    }

}
