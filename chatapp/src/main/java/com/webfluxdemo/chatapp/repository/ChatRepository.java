package com.webfluxdemo.chatapp.repository;

import com.webfluxdemo.chatapp.Entity.Chat;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    Flux<Chat> mFindBySenderAndReceiver(String sender, String receiver);
}
