package com.example.blackbell_order.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public class OrderDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateRequestDTO {
        private String orderId;

        private String productId;

        private String accountId;

        private Integer quantity;

        private Integer unitPrice;

        private Integer totalPrice;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateResponseDTO {
        private String orderId;

        private String productId;

        private String accountId;

        private Integer quantity;

        private Integer unitPrice;

        private Integer totalPrice;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetResponseDTO {
        private String orderId;

        private String productId;

        private Integer quantity;

        private Integer unitPrice;

        private Integer totalPrice;

        private Date createdAt;
    }
}
