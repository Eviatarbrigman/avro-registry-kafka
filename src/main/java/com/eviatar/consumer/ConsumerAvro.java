package com.eviatar.consumer;

import com.eviatar.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerAvro {


//    @KafkaListener(topics = "${topic.name}")
    @KafkaListener(topics = "avro-topic12",groupId = "avro-group")
    public void getMessage(ConsumerRecord<String, User> consumerRecord) {
        String key = consumerRecord.key();
        User value = consumerRecord.value();
        log.info("key" + key + "and value " + value );

    }
}
