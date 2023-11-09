package com.flowsphere.plugin.rocketmq.consumer.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpressionTypeEnum {

    SQL92("SQL92"),

    TAG("tag"),

    ALL("*");

    private String value;

}
