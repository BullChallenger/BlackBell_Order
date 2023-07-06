package com.example.blackbell_order.service;

import com.example.blackbell_order.dto.OrderDTO.*;
import com.example.blackbell_order.entity.OrderEntity;
import com.example.blackbell_order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;

    private final OrderRepository orderRepository;

    @Override
    public CreateResponseDTO createOrder(CreateRequestDTO requestDTO) {
        requestDTO.setOrderId(UUID.randomUUID().toString());
        requestDTO.setTotalPrice(requestDTO.getUnitPrice() * requestDTO.getQuantity());

        OrderEntity theOrder = modelMapper.map(requestDTO, OrderEntity.class);
        OrderEntity savedOrder = orderRepository.save(theOrder);

        return modelMapper.map(savedOrder, CreateResponseDTO.class);
    }

    @Override
    public GetResponseDTO getOrderByOrderId(String orderId) {
        OrderEntity theOrder = orderRepository.findByOrderId(orderId);
        return modelMapper.map(theOrder, GetResponseDTO.class);
    }

    @Override
    public Iterable<GetResponseDTO> getOrdersByAccountId(String accountId) {
        Iterable<OrderEntity> orders = orderRepository.findByAccountId(accountId);
        List<GetResponseDTO> responseDTOList = new ArrayList<>();

        orders.forEach(order -> {
            responseDTOList.add(modelMapper.map(order, GetResponseDTO.class));
        });

        return responseDTOList;
    }
}
