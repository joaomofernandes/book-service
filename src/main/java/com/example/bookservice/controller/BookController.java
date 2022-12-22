package com.example.bookservice.controller;

import com.example.bookservice.model.Book;
import com.example.bookservice.proxy.CurrencyProxy;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.response.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;
    @Autowired
    private BookRepository repository;
    @Autowired
    private CurrencyProxy proxy;

    /*@GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency){

        Book book = repository.getById(id);
        if(book == null) throw new RuntimeException("Book not found!");

        Map<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);
        var response = new RestTemplate().getForEntity("http://localhost:8000/currency-service/{amount}/{from}/{to}",
                Currency.class,
                params);

        var exchange = response.getBody();

        String port = environment.getProperty("local.server.port");
        book.setEnvironment(port);
        book.setPrice(exchange.getConvertedValue());
        return book;

    }*/
    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency){

        Book book = repository.getById(id);
        if(book == null) throw new RuntimeException("Book not found!");


        var exchange = proxy.getCurrency(book.getPrice(), "USD", currency);

        String port = environment.getProperty("local.server.port");
        book.setEnvironment("Book port: " + port + " Currency port: " + exchange.getEnvironment());
        book.setPrice(exchange.getConvertedValue());
        return book;

    }

}
