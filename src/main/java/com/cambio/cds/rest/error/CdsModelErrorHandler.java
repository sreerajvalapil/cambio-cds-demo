package com.cambio.cds.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CdsModelErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = {CdsValidationException.class})
    protected ResponseEntity<CdsInvalidRequest> handleError(
            CdsValidationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type","application/json")
                .body(CdsInvalidRequest.builder().violations(ex.getViolations()).build());
    }


}
