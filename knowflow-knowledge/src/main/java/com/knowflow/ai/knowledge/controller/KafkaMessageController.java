package com.knowflow.ai.knowledge.controller;

import com.knowflow.ai.knowledge.dto.KafkaMessageRequest;
import com.knowflow.ai.knowledge.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/knowledge/kafka")
public class KafkaMessageController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaMessageController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/messages")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody KafkaMessageRequest request) {

        if (request == null || request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "message must not be blank");
            return ResponseEntity.badRequest().body(response);
        }

        new Thread(()->{
            int i = 0 ;
            while (true){
                try {
                    Thread.sleep(500);
                    kafkaProducerService.send("sqs",i+"");
                    //kafkaProducerService.send("yh",i+"");
                    i++;
                }catch (Exception e){

                }
            }
        }).start();


        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "accepted");
        response.put("topic", kafkaProducerService.getTopic());

        return ResponseEntity.accepted().body(response);
    }


}
