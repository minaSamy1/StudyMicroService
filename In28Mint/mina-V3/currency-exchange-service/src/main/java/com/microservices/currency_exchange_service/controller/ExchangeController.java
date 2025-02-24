package com.microservices.currency_exchange_service.controller;

import com.microservices.currency_exchange_service.entity.CurrencyExchange;
import com.microservices.currency_exchange_service.repository.CurrencyExchangeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {
    @Autowired
    private CurrencyExchangeRepo repo;
    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange getExchange(@PathVariable String from, @PathVariable String to) {
        CurrencyExchange c = repo.findByFromAndTo(from, to);
        String port = environment.getProperty("local.server.port");
        c.setEnvironment(port);

        return c;
    }
}
