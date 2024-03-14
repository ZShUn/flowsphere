package com.flowsphere.agent.plugin.rocketmq;

import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
import com.flowsphere.agent.plugin.rocketmq.constant.RocketMQConstant;
import com.flowsphere.agent.plugin.rocketmq.consumer.config.ConsumerGroupConfig;

import java.util.List;

public class RocketMQConfigManager {

    public static String getModelType() {
        return (String) PluginConfigManager.getConfig(RocketMQConstant.ROCKETMQ, RocketMQConstant.MODEL_TYPE);
    }

    public static List<ConsumerGroupConfig> getConsumerGroupConfigList() {
        return (List<ConsumerGroupConfig>) PluginConfigManager.getConfig(RocketMQConstant.ROCKETMQ, RocketMQConstant.CONSUMER_GROUP_CONFIG);
    }

}
