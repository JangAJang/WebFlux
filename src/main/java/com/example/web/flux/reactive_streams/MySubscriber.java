package com.example.web.flux.reactive_streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySubscriber implements Subscriber<Integer> {

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(Integer integer) {
        System.out.println("Subscriber : 데이터 전달중");
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Subscriber : 데이터 수신중 에러");
    }

    @Override
    public void onComplete() {
        System.out.println("Subscriber : 받은 데이터 확인 완료");
    }
}
