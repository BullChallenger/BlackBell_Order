package com.example.blackbell_order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Field {

    private String type;

    private boolean optional;

    private String field;

    @Builder
    public Field(String type, boolean optional, String field) {
        this.type = type;
        this.optional = optional;
        this.field = field;
    }
}
