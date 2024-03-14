package com.flowsphere.agent.plugin.rocketmq.consumer.config;

import lombok.Data;

import java.util.List;

@Data
public class ConsumerGroupConfig {

    private String consumerGroupName;

    private String tags;

    private List<Integer> queueList;
}
