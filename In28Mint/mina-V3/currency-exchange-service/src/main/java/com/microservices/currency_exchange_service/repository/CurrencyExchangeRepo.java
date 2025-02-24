package com.microservices.currency_exchange_service.repository;

import com.microservices.currency_exchange_service.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepo  extends JpaRepository<CurrencyExchange , Long> {
    CurrencyExchange findByFromAndTo(String from , String to);

}
