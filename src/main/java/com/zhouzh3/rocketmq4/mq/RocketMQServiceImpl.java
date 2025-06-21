package com.zhouzh3.rocketmq4.mq;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author haig
 */
@Service
public class RocketMQServiceImpl implements RocketMQService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public SendResult syncSend(String topic, String tag, String message, long millis) {
     return   rocketMQTemplate.syncSend(topic + ":" + tag, message, millis);
    }

}
