package com.zhouzh3.rocketmq4.service;

import com.zhouzh3.rocketmq4.mq.event.OrderCancelledEvent;
import com.zhouzh3.rocketmq4.mq.event.OrderCreatedEvent;
import com.zhouzh3.rocketmq4.mq.event.OrderPaidEvent;
import com.zhouzh3.rocketmq4.mq.producer.OrderEventProducer;
import com.zhouzh3.rocketmq4.mq.types.OrderTags;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author haig
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderEventProducer orderEventProducer;

    @Override
    public SendResult createOrder(String orderId, String userId) {
        OrderCreatedEvent payload = new OrderCreatedEvent(orderId, userId, LocalDateTime.now());
        log.info("==发送消息{}", payload);
        return orderEventProducer.createOrder(OrderTags.ORDER_CREATED, orderId, payload);
    }

    @Override
    public SendResult payOrder(String orderId, String userId) {
        OrderPaidEvent payload = new OrderPaidEvent(orderId, userId, LocalDateTime.now());
        return orderEventProducer.sendOrderEvent(OrderTags.ORDER_PAID, orderId, payload);
    }

    @Override
    public SendResult cancelOrder(String orderId, String userId) {
        OrderCancelledEvent payload = new OrderCancelledEvent(orderId, userId, LocalDateTime.now());
        return orderEventProducer.sendOrderEvent(OrderTags.ORDER_CANCELLED, orderId, payload);
    }

}
