package com.example.ProductService.Exception;


import com.example.ProductService.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {


    @ExceptionHandler(ProductServiceCustomException.class)

    public ResponseEntity<ErrorResponse> handleException(ProductServiceCustomException exception) {


        return new ResponseEntity<>(ErrorResponse.builder().errorMessage(exception.getMessage()).errorCode(exception.getErrorCode()).build(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
