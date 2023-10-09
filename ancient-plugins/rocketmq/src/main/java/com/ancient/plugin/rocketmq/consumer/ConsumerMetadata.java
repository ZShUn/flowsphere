package com.ancient.plugin.rocketmq.consumer;

public class ConsumerMetadata {

    private String topic;

    private String subString;

    private String expressionType;


    public ConsumerMetadata(String topic, String subString, String expressionType) {
        this.topic = topic;
        this.subString = subString;
        this.expressionType = expressionType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubString() {
        return subString;
    }

    public void setSubString(String subString) {
        this.subString = subString;
    }

    public String getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(String expressionType) {
        this.expressionType = expressionType;
    }

}
