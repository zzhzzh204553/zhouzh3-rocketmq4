package com.zhouzh3.rocketmq4.mq.types;

/**
 * @author haig
 */

public interface OrderTags {
    /**
     * 订单创建
     */
    String ORDER_CREATED = "OrderCreated";
    /**
     * 订单支付
     */
    String ORDER_PAID = "OrderPaid";
    /**
     * 订单取消
     */
    String ORDER_CANCELLED = "OrderCancelled";


}
