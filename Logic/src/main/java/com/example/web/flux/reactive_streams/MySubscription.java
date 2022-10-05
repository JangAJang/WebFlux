package com.example.web.flux.reactive_streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Iterator;

//구독 정보
//데이터 수신자가 누구인지, 어떤 데이터를 수신하는지를 가지고 있어야 한다.
public class MySubscription implements Subscription {

    private Subscriber s;
    private Iterator<Integer> it;

    public MySubscription(Subscriber s, Iterable<Integer> its){
        this.s = s;
        this.it = its.iterator();
    }

    @Override
    public void request(long n) {
        while(n > 0){
            if(it.hasNext()){
                s.onNext(it.next());
            }
            else{
                s.onComplete();
                break;
            }
            n--;
        }
    }

    @Override
    public void cancel() {

    }
}
