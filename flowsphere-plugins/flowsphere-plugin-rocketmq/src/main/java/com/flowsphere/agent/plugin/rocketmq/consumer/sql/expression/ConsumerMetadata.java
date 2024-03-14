package com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression;

import com.flowsphere.agent.plugin.rocketmq.consumer.config.ConsumerGroupConfig;
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

    private ConsumerGroupConfig consumerGroupConfig;

}
