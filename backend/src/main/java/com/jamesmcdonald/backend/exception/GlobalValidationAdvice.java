package com.jamesmcdonald.backend.exception;


import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for validation errors.
 * This advice intercepts MethodArgumentNotValidException thrown when validation on an argument annotated with @Valid fails,
 * and returns a standardized HTTP 400 Bad Request response with problem details.
 */
@RestControllerAdvice
public class GlobalValidationAdvice {

    /**
     * Handles MethodArgumentNotValidException exceptions.
     * This method is triggered when validation on a request's method argument fails.
     * It returns a ResponseEntity containing a ProblemDetail with HTTP 400 status and a descriptive title.
     *
     * @param ex the exception thrown when method argument validation fails
     * @return a ResponseEntity with a ProblemDetail describing the validation error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex) {
        // Create a ProblemDetail instance with HTTP 400 Bad Request status
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid request content");

        // Return the ProblemDetail wrapped in a ResponseEntity with appropriate status and content type
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}