package com.eviatar.producer;

import com.eviatar.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProducerAvro {
    @Autowired
    private KafkaTemplate<String, User> template;

    @Value("${topic.name}")
    private String topicName;

    public void sendUser(User user) {
        try {
            CompletableFuture<SendResult<String, User>> future = template.send("avro-topic12", UUID.randomUUID().toString(), user);
            future.whenComplete((stringUserSendResult, throwable) -> {
                if (throwable == null) {
                    System.out.println("message that was sent : [ " +
                            user + "] with partition : [" +
                            stringUserSendResult.getRecordMetadata().partition() + "]");
                } else {
                    System.out.println("message was not send : [" + throwable.getMessage());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
