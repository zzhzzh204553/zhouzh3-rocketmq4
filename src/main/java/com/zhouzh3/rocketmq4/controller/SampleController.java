package com.zhouzh3.rocketmq4.controller;


import com.zhouzh3.rocketmq4.mq.producer.RocketMQService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Slf4j
@RestController
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    private RocketMQService rocketMQService;

    @GetMapping("/send")
    public SendResult sendMessage() {

        String message = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        log.info("message={}", message);
        // 发送字符串消息
        return rocketMQService.syncSend(
                "rocketmq4-topic",
                "create",
                "",
                message,
                3000
        );
    }
}
