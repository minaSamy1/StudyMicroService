package com.example.OrderService.Exception;


import com.example.OrderService.External.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handle(CustomException e) {

        return new ResponseEntity(ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .errorCode(e.getErrorCode()).build() , HttpStatus.INTERNAL_SERVER_ERROR );



    }
}
