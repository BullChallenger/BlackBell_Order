package com.example.blackbell_order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class KafkaOrderDTO implements Serializable {

    private Schema schema;

    private Payload payload;

    @Builder
    public KafkaOrderDTO(Schema schema, Payload payload) {
        this.schema = schema;
        this.payload = payload;
    }
}
