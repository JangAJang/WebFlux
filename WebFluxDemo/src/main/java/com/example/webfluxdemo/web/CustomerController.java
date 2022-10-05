package com.example.webfluxdemo.web;

import com.example.webfluxdemo.domain.Customer;
import com.example.webfluxdemo.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @GetMapping("/customer")
    public Flux<Customer> findAll(){
        return customerRepository.findAll().log();
    }
}
