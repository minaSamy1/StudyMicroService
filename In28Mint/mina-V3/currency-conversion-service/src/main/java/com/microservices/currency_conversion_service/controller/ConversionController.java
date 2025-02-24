package com.microservices.currency_conversion_service.controller;

import com.microservices.currency_conversion_service.entity.CurrencyConversion;
import com.microservices.currency_conversion_service.proxy.CurrencyExchageProxy;
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

// we make this configuration to Zipkin
@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

@RestController
public class ConversionController {

    @Autowired
    private CurrencyExchageProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        HashMap urlVariable = new HashMap();
        urlVariable.put("from", from);
        urlVariable.put("to", to);
        System.err.println(" >>>>>> Start call the Exchange api by RestTemplate <<<");
            // incase it work locally
        //   ResponseEntity<CurrencyConversion> apiResponseCall = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, urlVariable);
     // in case working wtih Docker will use the service name of currency service exchange(
        ResponseEntity<CurrencyConversion> apiResponseCall = new RestTemplate().getForEntity("http://currency-exchange:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, urlVariable);

        CurrencyConversion currencyConversion = apiResponseCall.getBody();
        return new CurrencyConversion(currencyConversion.getId(), currencyConversion.getFrom(), currencyConversion.getTo(), quantity, currencyConversion.getConversionMultiple(), quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " Rest");
        //return new CurrencyConversion(10001L, from, to, quantity, BigDecimal.ONE, BigDecimal.ONE, "");

    }

    @GetMapping("/currency-conversionFegin/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFegin(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        System.err.println(" >>>>>> Start call the Exchange api by Fegin Client <<<");

        CurrencyConversion currencyConversion = proxy.getExchange(from, to);
        return new CurrencyConversion(currencyConversion.getId(), currencyConversion.getFrom(), currencyConversion.getTo(), quantity, currencyConversion.getConversionMultiple(), quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " Fegin");

    }

}
