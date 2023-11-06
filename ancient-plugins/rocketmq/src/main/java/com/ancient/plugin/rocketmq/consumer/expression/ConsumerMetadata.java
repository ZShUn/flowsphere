package com.ancient.plugin.rocketmq.consumer.expression;

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

}
