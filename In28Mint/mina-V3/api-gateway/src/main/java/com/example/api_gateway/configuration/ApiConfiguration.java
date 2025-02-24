package com.example.api_gateway.configuration;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {


    @Bean
    public RouteLocator getRouter(RouteLocatorBuilder builder) {


        return builder.routes().route(p -> p.path("/get").filters(f -> f.addRequestHeader("MyHeader", "Header").addRequestParameter("Parm", "ParamValue")).uri("http://httpbin.org:80")).
                route(p -> p.path("/currency-exchange/**").uri("lb://CURRENCY-EXCHANGE")).
                route(p -> p.path("/currency-conversion/**").uri("lb://CURRENCY-CONVERSION")).
                route(p -> p.path("/currency-conversionFegin/**").uri("lb://CURRENCY-CONVERSION"))
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversionFegin/${segment}"))
                        .uri("lb://currency-conversion")).build();


    }
}

