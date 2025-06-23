package com.zhouzh3.rocketmq4.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author haig
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "rocketmq4-topic",
        consumerGroup = "rocketmq4-consumer",
        selectorExpression = "create"
)
public class SampleConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info(">>>>>>>>>>>>>>>Received: {}", message);
    }
}