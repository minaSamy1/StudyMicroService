package com.example.microservice.currencyconversionservice.controller;


import com.example.microservice.currencyconversionservice.entity.CurrencyConversion;
import com.example.microservice.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;


/// this for enable the loging in zipkin while using resttemplate

@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy exchangeServiceProxy;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")

    public CurrencyConversion convert(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        /// we define this map to but it's value in URL as a paramter  to the defined { from } , {to}
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> currencyExchangeResponse = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                uriVariables);


        return new CurrencyConversion(currencyExchangeResponse.getBody().getId(),
                from, to, quantity,
                currencyExchangeResponse.getBody().getConversionMultiple(),
                quantity.multiply(currencyExchangeResponse.getBody().getConversionMultiple()),
                currencyExchangeResponse.getBody().getEnvironment()+" Rest Template");


    }


    @GetMapping("/currency-conversion-fegin/from/{from}/to/{to}/quantity/{quantity}")

    public CurrencyConversion convertUsingFegin(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        CurrencyConversion currencyExchangeResponse = exchangeServiceProxy.retriveRate(from, to);


        return new CurrencyConversion(currencyExchangeResponse.getId(),
                from, to, quantity,
                currencyExchangeResponse.getConversionMultiple(),
                quantity.multiply(currencyExchangeResponse.getConversionMultiple()),
                currencyExchangeResponse.getEnvironment()+" Fegin ");


    }
}
