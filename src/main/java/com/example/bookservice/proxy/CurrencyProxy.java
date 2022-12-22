package com.example.bookservice.proxy;

import com.example.bookservice.response.Currency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "currency-service")
public interface CurrencyProxy {

    @GetMapping(value = "/currency-service/{amount}/{from}/{to}")
    public Currency getCurrency(
            @PathVariable("amount") Double amount,
            @PathVariable("from")String from,
            @PathVariable("to")String to);
}
