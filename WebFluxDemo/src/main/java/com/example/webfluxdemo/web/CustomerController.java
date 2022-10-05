package com.example.webfluxdemo.web;

import com.example.webfluxdemo.domain.Customer;
import com.example.webfluxdemo.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    //각 엘레멘트당 딜레이 1초씩 해서 로그는 1초마다 올라오지만 웹상으로는 총 딜레이 5초 이후에 한번에 json타입으로 결과가 나온다.
    @GetMapping("/flux")
    public Flux<Integer> flux(){
        return Flux.just(1, 2, 3, 4, 5).delayElements(Duration.ofSeconds(1)).log();
    }

    //각 엘레멘트당 딜레이 1초씩 로그와 웹 결과가 나온다.
    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> fluxstream(){
        return Flux.just(1, 2, 3, 4, 5).delayElements(Duration.ofSeconds(1)).log();
    }

    //여러 건 조회할 땐 Flux
    @GetMapping("/customer")
    public Flux<Customer> findAll(){
        return customerRepository.findAll().log();
    }

    //단건 조회할 땐 Mono
    @GetMapping("/customer/{id}")
    public Mono<Customer> findById(@PathVariable("id") Long id){
        return customerRepository.findById(id);
    }
}
