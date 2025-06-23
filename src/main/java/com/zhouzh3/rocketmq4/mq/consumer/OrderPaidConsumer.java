package com.zhouzh3.rocketmq4.mq.consumer;

import com.zhouzh3.rocketmq4.mq.event.OrderPaidEvent;
import com.zhouzh3.rocketmq4.mq.types.ConsumerGroups;
import com.zhouzh3.rocketmq4.mq.types.OrderTags;
import com.zhouzh3.rocketmq4.mq.types.OrderTopics;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author haig
 */
@RocketMQMessageListener(topic = OrderTopics.ORDER_EVENT,
        selectorExpression = OrderTags.ORDER_PAID,
        consumerGroup = ConsumerGroups.ORDER_PAID_GROUP)
public class OrderPaidConsumer implements RocketMQListener<OrderPaidEvent> {

    @Override
    public void onMessage(OrderPaidEvent message) {
        // 处理支付完成逻辑
    }
}
