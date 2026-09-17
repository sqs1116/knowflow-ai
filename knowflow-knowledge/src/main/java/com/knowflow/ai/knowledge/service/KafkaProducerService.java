package com.knowflow.ai.knowledge.service;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate,
                                @Value("${knowflow.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(String key, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);
        future.addCallback(result -> {
            RecordMetadata metadata = result.getRecordMetadata();
//            LOGGER.info("Kafka 生产一条消息: topic={}, partition={}, offset={}",
//                    metadata.topic(), metadata.partition(), metadata.offset());
        }, exception -> LOGGER.error("Failed to send Kafka message to topic {}", topic, exception));
    }

    public void send( String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(result -> {
            RecordMetadata metadata = result.getRecordMetadata();
//            LOGGER.info("Kafka 生产一条消息: topic={}, partition={}, offset={}",
//                    metadata.topic(), metadata.partition(), metadata.offset());
        }, exception -> LOGGER.error("Failed to send Kafka message to topic {}", topic, exception));
    }

    public String getTopic() {
        return topic;
    }
}
