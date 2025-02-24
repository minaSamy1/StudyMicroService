package com.example.microservice.currencyconversionservice.proxy;


import com.example.microservice.currencyconversionservice.entity.CurrencyConversion;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="dasdadasd",url="localhost:8000")
@FeignClient(name="currency-exchange-service")   /// kindly note we write
public interface CurrencyExchangeProxy {


    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retriveRate(@PathVariable String from, @PathVariable String to);
}
