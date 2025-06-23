package com.zhouzh3.rocketmq4.mq.types;

/**
 * ConsumerGroup 按业务语义划分（并支持水平扩展）
 * @author haig
 */
public interface ConsumerGroups {

    String ORDER_CANCELLED_GROUP = "order-cancelled-group";

    String ORDER_CREATED_GROUP = "order-created-group";

    String ORDER_PAID_GROUP = "order-paid-group";

}
