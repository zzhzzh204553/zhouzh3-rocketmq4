package com.zhouzh3.rocketmq4.controller;


import com.zhouzh3.rocketmq4.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_MS_FORMAT;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/create")
    public SendResult createOrder() {
        String orderId = "orderId-" + PURE_DATETIME_MS_FORMAT.format(new Date());
        String userId = "userId-" + ThreadLocalRandom.current().nextInt(1000, 10000);

        log.info("create order: orderId={}， userId={}", orderId, userId);
        return orderService.createOrder(orderId, userId);
    }
}
