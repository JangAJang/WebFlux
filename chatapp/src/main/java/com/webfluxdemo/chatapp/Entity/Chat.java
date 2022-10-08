package com.webfluxdemo.chatapp.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "CHAT")
public class Chat {

    @Id
    private String id;
    private String msg;
    private String sender;
    private String reveiver;

    private LocalDateTime createdAt;
}
