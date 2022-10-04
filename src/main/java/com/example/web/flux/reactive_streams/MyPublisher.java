package com.example.web.flux.reactive_streams;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;

public class MyPublisher implements Publisher<Integer> {

    Iterable<Integer> its = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    @Override
    public void subscribe(Subscriber<? super Integer> s) {
        System.out.println("1. 데이터 수신자가 데이터 제공 요청");
        System.out.println("2. 데이터 제공자가 데이터 구독 정보를 만들어서 반환 ");
        MySubscription subscription = new MySubscription(s, its);
        System.out.println("데이터 제공자 : 구독 정보 생성 완료");
        s.onSubscribe(subscription);
    }
}
