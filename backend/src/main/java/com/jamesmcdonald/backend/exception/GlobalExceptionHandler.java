package com.jamesmcdonald.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Provides centralised exception handling for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResponseStatusException of type NOT_FOUND and returns a structured error response.
     *
     * @param e the ResponseStatusException thrown
     * @return a map containing error details including timestamp, status, error, and message
     */
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(ResponseStatusException e) {
        return Map.of(
                // Current timestamp when the error occurred
                "timestamp", LocalDateTime.now(),
                // HTTP status code for Not Found
                "status", HttpStatus.NOT_FOUND.value(),
                // Error description
                "error", "Not Found",
                // Detailed message from the exception
                "message", e.getReason()
        );
    }
}
