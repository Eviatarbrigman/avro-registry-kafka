package com.eviatar.consumer;

import com.eviatar.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class ConsumerAvro {

    final String topic = "avro-topic12";


    //    @KafkaListener(topics = "${topic.name}")
    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 3000, multiplier = 2, maxDelay = 15000), exclude = {NullPointerException.class})
    @KafkaListener(topics = topic, groupId = "avro-group")
    public void getMessage(ConsumerRecord<String, Employee> consumerRecord) {
        try {
            String key = consumerRecord.key();
            Employee value = consumerRecord.value();
            log.info("key" + key + "and value " + value);

            List<Long> banedIdList = Stream.of(1234L, 4321L).toList();
            Long idAsLong = Long.parseLong(consumerRecord.value().getId().toString());
            if (banedIdList.contains(idAsLong)){
                throw new RuntimeException("this Id is band");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DltHandler
    public void retryTopic(ConsumerRecord<String, Employee> consumerRecord) {

        log.info("the issue is with topic : {} ,offset : {} and id : {}", consumerRecord.topic(), consumerRecord.offset(), consumerRecord.value().getId());

    }
}
