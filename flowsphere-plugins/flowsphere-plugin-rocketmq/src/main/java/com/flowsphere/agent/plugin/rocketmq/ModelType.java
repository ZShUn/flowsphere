package com.flowsphere.agent.plugin.rocketmq;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModelType {

    SQL92("sql92"),

    QUEUE("queue");

    private String modelType;

}
