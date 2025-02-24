package com.example.microservice.currencyexchangeservice.controller;

import com.example.microservice.currencyexchangeservice.entity.CurrencyExchange;
import com.example.microservice.currencyexchangeservice.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController

public class ExchangeController {
@Autowired
    private ExchangeRepository exchangeRepository;
@Autowired
private Environment environment;
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retriveRate(@PathVariable String from, @PathVariable String to) {

String port= environment.getProperty("local.server.port");
        CurrencyExchange currencyExchange = exchangeRepository.findByFromAndTo(from,to);
        if( currencyExchange==null)
        {


            throw  new RuntimeException(" Can not find to currancy Rate ");

        }

                //new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
        currencyExchange.setEnvironment(port);

        return currencyExchange;
    }
}
