package com.example.blackbell_order.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Date;

public class OrderVO {

    @Getter
    public static class CreateRequestVO {
        private String productId;

        private Integer quantity;

        private Integer unitPrice;
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseVO {
        private String orderId;

        private String productId;

        private Integer quantity;

        private Integer unitPrice;

        private Integer totalPrice;

        private Date createdAt;
    }
}
