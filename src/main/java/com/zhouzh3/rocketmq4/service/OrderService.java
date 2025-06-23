package com.zhouzh3.rocketmq4.service;

import org.apache.rocketmq.client.producer.SendResult;

public interface OrderService {
    SendResult createOrder(String orderId, String userId);

    SendResult payOrder(String orderId, String userId);

    SendResult cancelOrder(String orderId, String userId);
}
