package com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression;

import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerMetadata {

    private String topic;

    private String subString;

    private String expressionType;

    private RocketMQConfig.ConsumerConfig consumerConfig;

}
