package com.example.blackbell_order.messageQ;

import com.example.blackbell_order.dto.Field;
import com.example.blackbell_order.dto.KafkaOrderDTO;
import com.example.blackbell_order.dto.OrderDTO.CreateRequestDTO;
import com.example.blackbell_order.dto.Payload;
import com.example.blackbell_order.dto.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            Field.builder().type("string").optional(true).field("order_id").build(),
            Field.builder().type("string").optional(true).field("account_id").build(),
            Field.builder().type("string").optional(true).field("product_id").build(),
            Field.builder().type("int32").optional(true).field("quantity").build(),
            Field.builder().type("int32").optional(true).field("unit_price").build(),
            Field.builder().type("int32").optional(true).field("total_price").build()
    );

    Schema theSchema = Schema.builder()
                                .type("struct")
                                .fields(fields)
                                .optional(false)
                                .name("orders")
                                .build();

    public void send(String topic, CreateRequestDTO requestDTO) {
        Payload thePayload = Payload.builder()
                                    .order_id(requestDTO.getOrderId())
                                    .account_id(requestDTO.getAccountId())
                                    .product_id(requestDTO.getProductId())
                                    .quantity(requestDTO.getQuantity())
                                    .unit_price(requestDTO.getUnitPrice())
                                    .total_price(requestDTO.getTotalPrice())
                                    .build();

        KafkaOrderDTO theKafkaOrderDTO = KafkaOrderDTO.builder()
                                                        .schema(theSchema)
                                                        .payload(thePayload)
                                                        .build();

        String requestDTOJson = "";
        try {
            requestDTOJson = objectMapper.writeValueAsString(theKafkaOrderDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, requestDTOJson);
        log.info("Order Producer sent data from the Order Micro Service : " + theKafkaOrderDTO);
    }
}
