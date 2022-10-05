package com.example.web.flux.reactive_streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySubscriber implements Subscriber<Integer> {

    private Subscription s;

    @Override
    public void onSubscribe(Subscription s) {
        this.s = s;
        System.out.println("데이터 수신자 구독 정보 수신 완료");
        System.out.println("Subscriber requests ");
        s.request(7); //여기서 리퀘스트는 수신할 데이터의 수량
    }

    @Override
    public void onNext(Integer integer) {
        System.out.println("Subscriber : 데이터 " + integer + "개 전달");
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Subscriber : 데이터 수신중 에러");
    }

    @Override
    public void onComplete() {
        System.out.println("Subscriber : 모든 데이터 수신 완료");
    }
}
