package com.msucil.dev.springbase.core.exception.handler;


/**
 * Define Java Bean Validation Error Standard and Custom
 * Used as custom response while returning to client
 *
 * Used by Exception Handler
 */
public record FieldError(String field, Object rejectedValue, String message) {

}
