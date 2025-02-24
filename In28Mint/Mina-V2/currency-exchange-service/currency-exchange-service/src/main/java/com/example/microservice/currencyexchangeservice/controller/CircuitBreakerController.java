package com.example.microservice.currencyexchangeservice.controller;



import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import io.github.resilience4j.bulkhead.annotation.Bulkhead;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
@RestController
public class CircuitBreakerController {
    private static final  Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/sample-api")
  //  @Retry(name = "default")
///@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    @RateLimiter(name="default")
    private String get()
    {
      logger.info(" Hey >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

       // logger.info(">>>>>>>>");
//
       System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>> ");
       //throw new RuntimeException("asdasd");
     //   logger.info("Sample api call received");
//		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
//					String.class);
		return null ;
     //return "Sample App" ;
    }

    public String hardcodedResponse(Exception ex) {
        return "fallback-response";
    }
}
