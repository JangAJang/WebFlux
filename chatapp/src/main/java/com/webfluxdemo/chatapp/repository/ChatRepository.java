package com.webfluxdemo.chatapp.repository;

import com.webfluxdemo.chatapp.Entity.Chat;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    @Tailable // 커서를 닫지 않고 유지한다. 저장되어있던 데이터를 다 보내고 끝내는게 아니라, 데이터가 더 들어오면 FindBySenderAndReceiver로 계속 흘려보내준다.
    @Query("{sender: ?0, receiver : ?1}") //mongoDB 문법
    Flux<Chat> findBySenderAndReceiver(String sender, String receiver); //Flux : response를 유지하면서 데이터를 계속 보내기

    @Tailable
    @Query("{ chattingRoomNumber :  ?0}")
    Flux<Chat> findByChattingRoomNumber(Long chattingRoomNumber);
}
