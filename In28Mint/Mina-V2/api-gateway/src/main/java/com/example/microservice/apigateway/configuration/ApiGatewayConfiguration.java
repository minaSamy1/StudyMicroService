package com.example.microservice.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {


    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {


        return builder.routes().route(p -> p.path("/get").filters(f -> f
                                .addRequestHeader("MyHeader", "Header Value")
                                .addRequestParameter(" param1", "param1 Value"))
                        .uri("http://httpbin.org:80"))
                //// lb://CURRENCY-EXCHANGE-SERVICE

                .route(p -> p.path("/currency-exchange/**").uri("lb://CURRENCY-EXCHANGE-SERVICE"))    // kindly note  (currency-exchange) it'a the api name in  (currency-exchange-service) project
                .route(p -> p.path("/currency-conversion-fegin/**").uri("lb://currency-conversion-service")) // kindly note  (currency-conversion-fegin) it'a the api name in  (ccurrency-conversion-service) project
                .route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion-service"))        // kindly note  (currency-conversion-fegin) it'a the api name in  (ccurrency-conversion-service) project
                .route(p -> p.path("/currency-conversion-new/**").filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)", "/currency-conversion-fegin/${segment}"))
                        .uri("lb://currency-conversion-service"))
                ///   .route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion-service"))
//                .route(p -> p.path("/currency-conversion-feign/**")
                ///                     .uri("lb://CURRENCY-CONVERSION-SERVICE"))
                .build();
    }


}

