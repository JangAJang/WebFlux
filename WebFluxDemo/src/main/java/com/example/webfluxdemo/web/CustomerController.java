package com.example.webfluxdemo.web;

import com.example.webfluxdemo.domain.Customer;
import com.example.webfluxdemo.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    //Sink는 서로 다른 Flux요청이 들어왔을 때, 그 요청을 하나로 merge시켜준다.
    private final Sinks.Many<Customer> customerMany = Sinks.many().multicast().onBackpressureBuffer();

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
    @GetMapping(value = "/customer",  produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Customer> findAll(){
        return customerRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
    }

    //단건 조회할 땐 Mono
    @GetMapping("/customer/{id}")
    public Mono<Customer> findById(@PathVariable("id") Long id){
        return customerRepository.findById(id);
    }

    /*

    @GetMapping(value = "/customer/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> findAllSSE(){
        return customerRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
    }



    자바스크립트에서 event 로 받아온다.
    이런 방식으로 하면 SSE 프로토콜이 적용된다.
    "/customer/"와 달리 내용을 웹에 보여줄 때 data:가 앞에 붙는다
    위에서 만든 Sink를 asFlux로 Flux형 응답으로 만들어 줄 수 있다.
    sink는 여기서 ServerSentEvent형태로 매핑되어있고, 내부에 builder함수를 통해 추가 데이터를 build시킬 수 있다.
    그렇기에 함수를 Flux<ServerSentEvent<Customer>>형으로 바꾸어 준다.
    실제로 사용할 때에는 Customer대신에 ResponseDto를 사용하면 될 것 같다는 생각이 든다.
    더해진 데이터가 없으면 Sink로 인해 웹이 로딩된 상태로 계속 유지된다.
    이 때에는 customer때 처럼 기존에 있던 데이터를 받아오지는 못한다.
    로딩을 멈추었다가 다시 로딩을 시켜도 데이터가 계속 받아지지는 않는다.
    여기에서 doOnCancel(()->{Sink형변수.asFlux().blockLast()});를 통해 마지막 데이터가 들어왔다고 강제적으로 설정해주는 것이다.
    이를 통해 해당 클라이언트가 마지막으로 받은 데이터가 무엇인지 알 수 있고, 이후에 sse프로토콜을 재요청할 때 이후 데이터를 게속해서 받을 수 있다.
     */
    @GetMapping("/customer/sse")//produces = MediaType.TEXT_EVENT_STREAM_VALUE <- 생략 가능
    public Flux<ServerSentEvent<Customer>> findAllSSE(){
        return customerMany.asFlux().map(c -> ServerSentEvent.builder(c).build()).doOnCancel(()->{
            customerMany.asFlux().blockLast();
        });
    }

    //그냥 repository.save(new Entity)를 해주면 sse프로토콜을 요청한 클라이언트가 해당 데이터를 받아서 새로 출력할 수 없다.
    //그래서 .doOnNext( variable -> {Sink형 변수.tryEmitNext(variable)})를 붙여주어야 한다.
    //여기에서는 람다식으로 표현했다.
    @PostMapping("/customer/add")
    public Mono<Customer> save(){
        return customerRepository.save(new Customer("Janghee", "Lee")).doOnNext(customerMany::tryEmitNext);
    }
}
