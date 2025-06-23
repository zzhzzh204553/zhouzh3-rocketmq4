package com.zhouzh3.rocketmq4.mq.consumer;

import com.zhouzh3.rocketmq4.mq.event.OrderCreatedEvent;
import com.zhouzh3.rocketmq4.mq.types.ConsumerGroups;
import com.zhouzh3.rocketmq4.mq.types.OrderTags;
import com.zhouzh3.rocketmq4.mq.types.OrderTopics;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;


/**
 * consumeMode = ConsumeMode.ORDERLY
 * 消费者端使用注解配置顺序消费：
 */
@RocketMQMessageListener(topic = OrderTopics.ORDER_EVENT,
        selectorExpression = OrderTags.ORDER_CREATED,
        consumerGroup = ConsumerGroups.ORDER_CREATED_GROUP,
        consumeMode = ConsumeMode.ORDERLY)
public class OrderCreatedConsumer implements RocketMQListener<OrderCreatedEvent> {

    @Override
    public void onMessage(OrderCreatedEvent message) {
        // 处理订单创建逻辑
    }
}
