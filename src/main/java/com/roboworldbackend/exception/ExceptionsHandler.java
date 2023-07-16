package com.roboworldbackend.exception;

import com.roboworldbackend.model.exception.ErrorInformation;
import com.roboworldbackend.model.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

/**
 * Exceptions handler
 *
 * @author Blajan George
 */
@ControllerAdvice
public class ExceptionsHandler {

    /**
     * Handle {@link MethodArgumentNotValidException}
     *
     * @param ex Exception instance
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        new ErrorInformation(
                                ex.getBindingResult().getFieldError() != null ? ex.getBindingResult().getFieldError().getDefaultMessage()
                                        : "",
                                ex.getClass().getSimpleName())));
    }

    /**
     * Handle {@link HttpMessageNotReadableException}
     *
     * @param ex Exception instance
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        new ErrorInformation(
                                ex.getMessage(),
                                ex.getClass().getSimpleName())));
    }

    /**
     * Handle {@link HttpMessageNotReadableException}
     *
     * @param ex Exception instance
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgException(final IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        new ErrorInformation(
                                ex.getMessage(),
                                ex.getClass().getSimpleName())));
    }

    /**
     * Handle {@link @DataIntegrityViolationException}
     *
     * @param ex Exception instance
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityException(final DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponse(
                        new ErrorInformation(
                                String.format("%s %s", ex.getMostSpecificCause().getMessage(), ((SQLException) ex.getMostSpecificCause()).getSQLState()),
                                ex.getClass().getSimpleName())));
    }

    /**
     * Handle {@link EntityNotFoundException}
     *
     * @param ex Exception instance
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        new ErrorInformation(
                                ex.getMessage(),
                                ex.getClass().getSimpleName())));
    }

    /**
     * Handle {@link InvalidJWTException}
     *
     * @param ex Exception instance
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(InvalidJWTException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJWTException(final InvalidJWTException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponse(
                        new ErrorInformation(
                                ex.getMessage(),
                                ex.getClass().getSimpleName())));
    }

    /**
     * Handle {@link io.jsonwebtoken.ExpiredJwtException}
     *
     * @param ex Exception instance
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(final ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(
                        new ErrorInformation(
                                ex.getMessage(),
                                ex.getClass().getSimpleName())));
    }
}

