package com.flowsphere.agent.core.plugin.config.support;

import lombok.Data;

import java.util.List;

@Data
public class RocketMQConfig {

    private String modelType;

    private List<RocketMQConfig.ConsumerConfig> consumerConfigList;

    private List<RocketMQConfig.ProducerConfig> producerConfigList;


    @Data
    public static class ConsumerConfig {

        private String consumerGroupName;

        private String tags;

        private List<Integer> queueList;

    }

    @Data
    public static class ProducerConfig {

        private String topic;

        private List<Integer> queueIdList;

    }

}
