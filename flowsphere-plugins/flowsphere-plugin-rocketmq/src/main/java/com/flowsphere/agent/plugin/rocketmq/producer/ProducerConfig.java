package com.flowsphere.agent.plugin.rocketmq.producer;

import lombok.Data;

import java.util.List;

@Data
public class ProducerConfig {

    private String topic;

    private List<Integer> queueIdList;

}
