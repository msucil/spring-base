package com.msucil.dev.springbase.core.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.msucil.dev.springbase.core.web.ResponseDetail;

public abstract class DefaultExceptionHandler {

    protected ResponseEntity<ResponseDetail> handleError(ResponseDetail body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }
}
