package com.example.ticketingcatalog.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private String generateTraceId() {
        String traceId = UUID.randomUUID().toString();
        return traceId;
    }

    private ResponseEntity<ErrorResponse> build(
            Exception ex,
            HttpStatus status,
            HttpServletRequest request,
            String title
    ) {
        String traceId = generateTraceId();

        ErrorResponse error = new ErrorResponse(
                "https://api.ticketing.com/errors/" + status.value(),
                title,
                status.value(),
                ex.getMessage(),
                request.getRequestURI(),
                traceId,
                Instant.now()
        );

        log.error("Error en request={} | status={} | traceId={}", request.getRequestURI(), status.value(), traceId, ex);

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        String traceId = generateTraceId();

        String details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        ErrorResponse error = new ErrorResponse(
                "https://api.ticketing.com/errors/400",
                "Validation Error",
                400,
                details,
                request.getRequestURI(),
                traceId,
                Instant.now()
        );

        log.warn("Validación fallida en request={} | traceId={} | detalles={}", request.getRequestURI(), traceId, details);

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return build(ex, HttpStatus.NOT_FOUND, request, "Resource Not Found");
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException ex, HttpServletRequest request) {
        return build(ex, HttpStatus.CONFLICT, request, "Duplicate Resource");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        return build(ex, HttpStatus.BAD_REQUEST, request, "Bad Request");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        return build(ex, HttpStatus.CONFLICT, request, "Data Integrity Violation");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
        return build(ex, HttpStatus.INTERNAL_SERVER_ERROR, request, "Unexpected Server Error");
    }
}
