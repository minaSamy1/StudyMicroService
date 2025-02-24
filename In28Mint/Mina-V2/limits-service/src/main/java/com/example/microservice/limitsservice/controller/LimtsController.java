package com.example.microservice.limitsservice.controller;


import com.example.microservice.limitsservice.configuration.Configuration;
import com.example.microservice.limitsservice.entity.Limits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimtsController {
    @Autowired

    private Configuration configuration;
//@Value("${limits-service.minimum}")
//    private int min;
//
//    @Value("${limits-service.maximum}")
//    private int max;
    @GetMapping("/limits")
    public Limits getAllLimits() {
 return new Limits(configuration.getMinimum(), configuration.getMaximum());
     //   return new Limits(min, max);

        //   return new Limits(1, 1000);
    }

}
