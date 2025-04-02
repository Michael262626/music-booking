package com.africa.musicbookingapp.music_booking.exception.globalExceptionHandler;

import com.africa.musicbookingapp.music_booking.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleUserNotFound(ResourceNotFoundException exception) {
        return ResponseEntity.status(BAD_REQUEST).body(Map.of(
                "error", exception.getMessage(),
                "success", false
        ));
    }
    @ExceptionHandler(AuthenticationServiceException.class)
    @ResponseBody
    public ResponseEntity<?> handleAuthenticationException(AuthenticationServiceException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "error", exception.getMessage(),
                "success", false
        ));
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception exception) {
        return ResponseEntity.status(BAD_REQUEST).body(Map.of(
                "error", exception.getMessage(),
                "success", false
        ));
    }
}
