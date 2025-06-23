package com.zhouzh3.rocketmq4.mq.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author haig
 */
@Slf4j
@Component
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class OrderTransactionListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String orderId = (String) arg;
        log.info("执行本地事务，订单ID：{}", orderId);
        // 执行本地事务逻辑，比如保存订单、扣库存等

        try {
            // 如果成功
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 这个 checkLocalTransaction(Message msg) 方法是在使用事务消息时，由 Broker 主动触发的，用于“回查”本地事务执行状态的。
     *     * 典型调用时机如下：
     *     * 事务半消息发送成功后，本地执行逻辑异常或者没有明确提交/回滚时 当你通过 sendMessageInTransaction 发送事务消息时，消息首先是以“半消息”的形式存储在 Broker 上。这时 Broker 会等待你在 executeLocalTransaction(...) 方法中返回事务执行的结果（COMMIT/ROLLBACK/UNKNOWN）。
     *     * 如果你在 executeLocalTransaction 中返回了 UNKNOWN，或者因为应用异常/宕机没有返回结果，Broker 就会在一定时间后（默认1分钟）调用 checkLocalTransaction(Message msg) 来“回查”你这条消息的事务状态。
     *     * 这个回查会反复执行，直到你返回明确的 COMMIT 或 ROLLBACK，或者超出回查次数被丢弃
     *     * 你可以在 checkLocalTransaction 中通过消息的一些标识（比如 key 或 tag）查找本地事务日志，判断事务是成功、失败还是依旧未知。
     * */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("检查本地事务状态，消息ID：{}", msg.getHeaders());
        // 根据消息状态做回查（可选）
        return RocketMQLocalTransactionState.COMMIT;
    }
}
