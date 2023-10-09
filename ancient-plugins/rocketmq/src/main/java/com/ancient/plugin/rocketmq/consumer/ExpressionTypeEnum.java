package com.ancient.plugin.rocketmq.consumer;

public enum ExpressionTypeEnum {

    SQL92("SQL92"),

    TAG("tag"),

    ALL("*");


    ExpressionTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
