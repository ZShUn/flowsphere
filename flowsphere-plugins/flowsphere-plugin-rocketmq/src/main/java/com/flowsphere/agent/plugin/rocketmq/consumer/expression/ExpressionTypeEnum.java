package com.flowsphere.agent.plugin.rocketmq.consumer.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpressionTypeEnum {

    SQL92("SQL92"),

    TAG("TAG"),

    ALL("*");

    private String value;

}
