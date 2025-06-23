package com.zhouzh3.rocketmq4.mq.producer;

import com.zhouzh3.rocketmq4.mq.event.OrderCreatedEvent;
import com.zhouzh3.rocketmq4.mq.types.OrderTopics;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Service;


@Service
public class OrderEventProducer {

    private final RocketMQService rocketMQService;

    public OrderEventProducer(RocketMQService rocketMQService) {
        this.rocketMQService = rocketMQService;
    }

    public <T> SendResult sendOrderEvent(String tag, String key, T payload) {
        return rocketMQService.syncSend(OrderTopics.ORDER_EVENT, tag, key, payload, 3000);
    }

    public SendResult createOrder(String tag, String key, OrderCreatedEvent payload) {
        return rocketMQService.sendMessageInTransaction(OrderTopics.ORDER_EVENT, tag, key, payload, payload.getOrderId());
    }


}
