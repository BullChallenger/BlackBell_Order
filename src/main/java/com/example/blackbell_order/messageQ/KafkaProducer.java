package com.example.blackbell_order.messageQ;

import com.example.blackbell_order.dto.OrderDTO.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, CreateRequestDTO requestDTO) {
        String requestDTOJson = "";
        try {
            requestDTOJson = objectMapper.writeValueAsString(requestDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, requestDTOJson);
        log.info("Kafka Producer sent data from the Order Micro Service : " + requestDTO);
    }
}
