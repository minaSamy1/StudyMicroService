package com.example.microservice.apigateway.configuration;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class LoggingGlobalFilter implements GlobalFilter {


//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String originalUri = exchange.getRequest().getURI().toString();
//        System.out.println("/////////>>>>>>>>>>>>>>>. Original URL: " + originalUri);
//        return chain.filter(exchange);
//    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String originalUri = request.getURI().toString();
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpStatus status = (HttpStatus) response.getStatusCode();
            System.out.println(">>>>>>>>>>>> Response status: " + status);
            response.getHeaders().forEach((header, values) -> System.out.println(header + ":" + values));
            String finalUrl = getFinalUrl(request, response);
            if (finalUrl != null) {
                System.out.println("Final URL: " + finalUrl);
            } else {
                System.out.println("Final URL: " + originalUri);
            }
        }));
    }

    private String getFinalUrl(ServerHttpRequest request, ServerHttpResponse response) {
        URI requestUri = request.getURI();
        HttpStatus status = (HttpStatus) response.getStatusCode();
        URI responseUri = status.is3xxRedirection() ? response.getHeaders().getLocation() : null;
        return responseUri != null ? responseUri.toString() : null;
    }
}
