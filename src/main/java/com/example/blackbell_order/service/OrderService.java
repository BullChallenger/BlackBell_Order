package com.example.blackbell_order.service;

import com.example.blackbell_order.dto.OrderDTO.*;
import com.example.blackbell_order.entity.OrderEntity;

public interface OrderService {
    CreateResponseDTO createOrder(CreateRequestDTO requestDTO);

    GetResponseDTO getOrderByOrderId(String orderId);

    Iterable<GetResponseDTO> getOrdersByAccountId(String accountId);
}
