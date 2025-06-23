package com.zhouzh3.rocketmq4.mq.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author haig
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private String orderId;
    private String userId;
    private LocalDateTime createTime;


}
