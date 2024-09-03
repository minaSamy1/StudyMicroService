package com.example.microservice.currencyexchangeservice.repository;

import com.example.microservice.currencyexchangeservice.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository   extends JpaRepository<CurrencyExchange ,Long> {


    CurrencyExchange findByFromAndTo(String from , String to);
}
