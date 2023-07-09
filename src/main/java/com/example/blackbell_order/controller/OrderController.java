package com.example.blackbell_order.controller;

import com.example.blackbell_order.constant.ResultType;
import com.example.blackbell_order.dto.OrderDTO.*;
import com.example.blackbell_order.dto.ResponseDTO;
import com.example.blackbell_order.entity.OrderEntity;
import com.example.blackbell_order.messageQ.KafkaProducer;
import com.example.blackbell_order.messageQ.OrderProducer;
import com.example.blackbell_order.service.OrderService;
import com.example.blackbell_order.vo.OrderVO.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order-service")
public class OrderController extends AbstractController {

    private final Environment env;
    private final ModelMapper modelMapper;

    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping(value = "/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping(value = "/{accountId}/orders")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<ResponseVO> create(@PathVariable("accountId") String accountId,
                                          @RequestBody CreateRequestVO requestVO) {

        log.info("Before add orders data");
        CreateRequestDTO requestDTO = modelMapper.map(requestVO, CreateRequestDTO.class);
        requestDTO.setAccountId(accountId);

        /* Jpa */
//        CreateResponseDTO responseDTO = orderService.createOrder(requestDTO);

        /* Kafka */
        requestDTO.setOrderId(UUID.randomUUID().toString());
        requestDTO.setTotalPrice(requestDTO.getUnitPrice() * requestDTO.getQuantity());

        ResponseVO responseVO = modelMapper.map(requestDTO, ResponseVO.class);

        /* Send this order to Kafka */
        kafkaProducer.send("example-catalog-topic", requestDTO);
        orderProducer.send("orders", requestDTO);

        log.info("After added orders data");
        return ResponseDTO.ok(ResultType.CREATE_ORDER_SUCCESS, responseVO);
    }

    @GetMapping(value = "/{accountId}/orders")
    public ResponseDTO<List<ResponseVO>> readByAccountId(@PathVariable("accountId") String accountId) {

        log.info("Before retrieve orders data");
        Iterable<GetResponseDTO> orderList = orderService.getOrdersByAccountId(accountId);
        List<ResponseVO> responseVOList = new ArrayList<>();

        orderList.forEach(order -> {
            responseVOList.add(modelMapper.map(order, ResponseVO.class));
        });
        log.info("Add retrieved orders data");

        return ResponseDTO.ok(ResultType.SUCCESS, responseVOList);
    }

    @GetMapping(value = "/orders/{orderId}")
    public ResponseDTO<ResponseVO> read(@PathVariable("orderId") String orderId) {
        GetResponseDTO responseDTO = orderService.getOrderByOrderId(orderId);
        return ResponseDTO.ok(ResultType.SUCCESS, modelMapper.map(responseDTO, ResponseVO.class));
    }
}
