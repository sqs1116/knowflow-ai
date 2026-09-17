package com.knowflow.ai.service.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageConsumer.class);

    @KafkaListener(topics = "${knowflow.kafka.topic}", groupId = "knowflow-knowledge-group"
    )
    public void consume(ConsumerRecord<String, String> record, Acknowledgment ack) {
        System.out.println("key="+record.key()
                +"partition=" + record.partition()
                + ", offset=" + record.offset()
                + ", message=" + record.value()
        );
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ack.acknowledge();
    }
}
