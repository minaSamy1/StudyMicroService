package com.example.restfulwebservices.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomResponseEntityException extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionErrorDetails> customhandleException(Exception ex, WebRequest request) throws Exception {
        ExceptionErrorDetails customException = new ExceptionErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));


        return new ResponseEntity<ExceptionErrorDetails>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomUserException.class)
    public final ResponseEntity<ExceptionErrorDetails> customhandleExceptionTwo(Exception ex, WebRequest request) throws Exception {
        ExceptionErrorDetails customException = new ExceptionErrorDetails(LocalDateTime.now(), ex.getMessage()+" the Second", request.getDescription(false));


        return new ResponseEntity<ExceptionErrorDetails>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionErrorDetails customException = new ExceptionErrorDetails(  LocalDateTime.now(),   "Total Errors:" + ex.getErrorCount() + " First Error:" + ex.getFieldError().getDefaultMessage(), request.getDescription(false));
        return new ResponseEntity(customException, HttpStatus.BAD_REQUEST);
    }
}
