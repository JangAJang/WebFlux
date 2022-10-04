package com.example.web.flux.reactive_streams;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class MyPublisher implements Publisher<Integer> {

    @Override
    public void subscribe(Subscriber<? super Integer> s) {
        System.out.println("1. 데이터 제공자가 데이터 수신자 확인");
        System.out.println("2. 데이터 수신 정보를 만들어줘야 한다. ");
    }
}
