// src/main/java/com/jamesmcdonald/backend/exception/GlobalExceptionHandler.java
package com.jamesmcdonald.backend.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetail> handleResponseStatusException(ResponseStatusException e) {
        ProblemDetail problem = ProblemDetail.forStatus(e.getStatusCode());

        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatusCode().value());
        problem.setTitle(httpStatus.getReasonPhrase());

        if (e.getReason() != null) {
            problem.setDetail(e.getReason());
        }

        return ResponseEntity
                .status(e.getStatusCode())
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}