package com.example.OrderService.External.decoder;

import com.example.OrderService.Exception.CustomException;
import com.example.OrderService.External.Response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +s);

        ObjectMapper objectMapper
                = new ObjectMapper();
        try {


            ErrorResponse errorResponse
                    = objectMapper.readValue(response.body().asInputStream(),
                    ErrorResponse.class);

            return new CustomException(errorResponse.getErrorMessage(),
                    errorResponse.getErrorCode(),
                    response.status());

        } catch (IOException e) {
            throw new CustomException("Internal Server Error",
                    "INTERNAL_SERVER_ERROR",
                    500);
        }
    }
}
