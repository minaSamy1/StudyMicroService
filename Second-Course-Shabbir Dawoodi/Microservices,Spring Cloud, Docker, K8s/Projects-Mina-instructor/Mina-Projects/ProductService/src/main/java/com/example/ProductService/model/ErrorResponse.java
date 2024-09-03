package com.example.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor



public class ErrorResponse {

    private String errorMessage;
    private String errorCode;



}
