package com.eviatar.producer;

import com.eviatar.model.Employee;
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
    private KafkaTemplate<String, Employee> template;

    @Value("${topic.name}")
    private String topicName;

    public void sendUser(Employee employee) {
        try {
            CompletableFuture<SendResult<String, Employee>> future = template.send("avro-topic12", UUID.randomUUID().toString(), employee);
            future.whenComplete((stringUserSendResult, throwable) -> {
                if (throwable == null) {
                    System.out.println("message that was sent : [ " +
                            employee + "] with partition : [" +
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
