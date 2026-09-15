package com.booking.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoResourceFound(
            NoResourceFoundException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Resource not found");
        problem.setDetail("The requested endpoint does not exist");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problem);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElement(
            NoSuchElementException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problem);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(problem);
    }

    @ExceptionHandler(RoomNumberAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleRoomNumberAlreadyExists(
            RoomNumberAlreadyExistsException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(problem);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> handleIllegalState(
            IllegalStateException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(problem);
    }

    @ExceptionHandler(RoomAlreadyBookedException.class)
    public ResponseEntity<ProblemDetail> handleRoomAlreadyBooked(
            RoomAlreadyBookedException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(problem);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ProblemDetail> handleResourceAccess(ResourceAccessException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setTitle(ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(problem);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.METHOD_NOT_ALLOWED);
        problem.setTitle("Method not allowed");
        problem.setDetail("HTTP method " + ex.getMethod() + " is not supported for this endpoint");
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(problem);
    }
}