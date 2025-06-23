package com.zhouzh3.rocketmq4.mq.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
//import org.springframework.messaging.Message;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * 通用的消息发送服务
 * @author haig
 */
@Slf4j
@Service
public class RocketMQService {

    private final RocketMQTemplate rocketMQTemplate;

    public RocketMQService(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 1. 普通消息
     * @param topic
     * @param tag
     * @param key
     * @param message
     * @param timeout
     * @return
     * @param <T>
     */
    public <T> SendResult syncSend(String topic, String tag, String key, T message, long timeout) {
        return syncSend(topic, tag, key, message, timeout, 0);
    }

    /**
     * 2.✅ 顺序消息（Ordered Message）
     * @param topic
     * @param tag
     * @param message
     * @param hashKey 来确保同一业务标识的消息进入同一个队列：use this key to select queue. for example: orderId, productId ...
     * @return
     */
    public SendResult syncSendOrderly(String topic, String tag, String message, String hashKey) {
        return rocketMQTemplate.syncSendOrderly(topic + ":" + tag, message, hashKey);
    }

    /**
     * 3.⏳ 延迟消息（Delayed Message）
     * @param topic
     * @param tag
     * @param payload
     * @param timeout 本次发送消息的最大等待时间（单位：毫秒）。
     * @param delayLevel 1=1秒，2=5秒，3=10秒，4=30秒，5=1分钟，6=2分钟，7=3分钟，8=4分钟，9=5分钟，10=6分钟，11=7分钟，12=8分钟，13=9分钟，14=10分钟，15=20分钟，16=30分钟，17=1小时，18=2小时
     * @return
     */
    public <T> SendResult syncSend(String topic, String tag, String key, T payload, long timeout, int delayLevel) {
        Message<?> message = MessageBuilder
                .withPayload(payload)
                .setHeader(RocketMQHeaders.KEYS, key)
                .build();
        log.info("<<<<<<<<<<<<<<<<topic={},tag={},message={}", topic, tag, message);
        return rocketMQTemplate.syncSend(topic + ":" + tag, message, timeout, delayLevel);
    }

    public <T> SendResult batchSend(String topic, String tag, String key, List<T> payloads) {
        List<org.apache.rocketmq.common.message.Message> messages = payloads.stream().map(it -> {
            byte[] bytes = JSON.toJSONString(it).getBytes(StandardCharsets.UTF_8);
            return new org.apache.rocketmq.common.message.Message(topic, tag, key, bytes);
        }).toList();

        try {
            return rocketMQTemplate.getProducer().send(messages);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            log.error("syncSend failed. destination:{}, message:{}, detail exception info: ", topic + ":" + tag, messages, e);
            throw new MessagingException(e.getMessage(), e);
        }
    }

    /**
     * 5. 事务消息（Transactional Message）
     * ✅ arg 的作用与传递流程
     * 当你发送事务消息时，RocketMQ 会：
     *
     * 先发送 Half 消息（暂时不可消费）；
     *
     * 调用本地事务监听器的 executeLocalTransaction(Message, Object arg) 方法；
     *
     * arg 就是你这里传入的第三个参数，可以用来传数据库主键、业务对象、上下文信息等；
     *
     * 后续用于事务回查 checkLocalTransaction(...)。
     * @param topic
     * @param tag
     * @param payload
     * @return
     */
    public <T> SendResult sendMessageInTransaction(String topic, String tag, String key, T payload, Object arg) {
        log.info("<<<<<<<<<<<<<<<<topic={}, tag={}, key={}, payload={}", topic, tag, key, payload);

        Message<?> message = MessageBuilder
                .withPayload(payload)
                .setHeader(RocketMQHeaders.KEYS, key)
                .build();
        return rocketMQTemplate.sendMessageInTransaction(topic + ":" + tag, message, arg);
    }


}
