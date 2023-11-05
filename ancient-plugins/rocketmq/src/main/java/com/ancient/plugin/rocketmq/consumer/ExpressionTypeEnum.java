package com.ancient.plugin.rocketmq.consumer;

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
