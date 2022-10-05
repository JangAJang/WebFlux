package com.example.web.flux.reactive_streams;

public class App {

    public static void main(String[] args){
        MyPublisher publisher = new MyPublisher();
        MySubscriber subscriber = new MySubscriber();
        publisher.subscribe(subscriber);
    }
}
