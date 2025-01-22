package com.nam.storespring.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    public void sendLog(String topic, String action, String id, Object obj) {
        try {
            String objJson = objectMapper.writeValueAsString(obj);
            String logMessage = String.format("Action: %s, Id: %s, Details: %s", action, id, objJson);
            kafkaTemplate.send(topic, logMessage);
        } catch (JsonProcessingException e) {
            System.err.println("Failed to convert to Json: " + e.getMessage());
        }

    }
}
