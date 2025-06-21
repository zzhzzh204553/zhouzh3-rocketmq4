package com.zhouzh3.rocketmq4.mq;

import org.apache.rocketmq.client.producer.SendResult;

public interface RocketMQService {
    SendResult syncSend(String topic, String tag, String message, long millis);
}
