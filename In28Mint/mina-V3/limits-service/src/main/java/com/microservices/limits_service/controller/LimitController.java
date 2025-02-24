package com.microservices.limits_service.controller;


import com.microservices.limits_service.configuration.Configuration;
import com.microservices.limits_service.entity.Limits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LimitController {

    @Autowired
    private Configuration configuration;
    // will read from local app.pro until  applying config cloud configuration will read the value from the GIT
    @Value("${limits-service.minimum}")
    private int min;
    @Value("${limits-service.maximum}")
    private int max;


    @GetMapping("/limits")
    public Limits getlimits() {


        return new Limits(min, max);   // reading from application.properities
        // return new Limits(configuration.getMinimum(), configuration.getMaximum());  // reading from Configuration Class

    }
}
