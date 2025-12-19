package com.revature.library.exception;

import com.revature.library.dto.ErrorResponseRecord;
import com.revature.library.dto.ErrorResponseRecordBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponseRecord> handleBookNotFound(BookNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<ErrorResponseRecord> handleBookNotAvailable(BookNotAvailableException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseRecord> handleValidationErrors(MethodArgumentNotValidException ex) {
        var message = ex.getBindingResult().getFieldErrors().stream()
                .filter(err -> err.getDefaultMessage() != null)
                .map(err -> Map.entry(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return buildErrorResponse(message.toString(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponseRecord> buildErrorResponse(String message, HttpStatus status) {
        var body = ErrorResponseRecordBuilder.builder()
                .message(message)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(body, status);
    }
}
