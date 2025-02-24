package com.microservices.currency_conversion_service.proxy;


import com.microservices.currency_conversion_service.entity.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// http://localhost:8000/currency-exchange/from/USD/to/INR

@FeignClient(name = "currency-exchange", url = "localhost:8000")  // here defining with serviceName and
//@FeignClient(name = "currency-exchange") //incase naming server
public interface CurrencyExchageProxy {


    // get the Signature of Api from the service controller and JUST replace the Object that will return to be [ CurrencyConversion] as it have the same field for Repsonse
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion getExchange(@PathVariable String from, @PathVariable String to);


}
