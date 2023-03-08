package com.mracover.if_else_task.exception_handler;

import com.mracover.if_else_task.exception_handler.exception.ConflictException;
import com.mracover.if_else_task.exception_handler.exception.ForbiddenException;
import com.mracover.if_else_task.exception_handler.exception.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<GlobalExceptionResponse> noSuchDataExceptionHandler(NoSuchDataException ex) {
        GlobalExceptionResponse globalExceptionResponse = new GlobalExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(globalExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<GlobalExceptionResponse> conflictEmailExceptionHandler(ConflictException ex) {
        GlobalExceptionResponse globalExceptionResponse = new GlobalExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(globalExceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalExceptionResponse> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        GlobalExceptionResponse globalExceptionResponse = new GlobalExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(globalExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<GlobalExceptionResponse> forbiddenExceptionHandler(ValidationException ex) {
        GlobalExceptionResponse globalExceptionResponse = new GlobalExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(globalExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<GlobalExceptionResponse> forbiddenExceptionHandler(ForbiddenException ex) {
        GlobalExceptionResponse globalExceptionResponse = new GlobalExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(globalExceptionResponse, HttpStatus.FORBIDDEN);
    }
}
