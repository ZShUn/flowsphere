package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.interceptor.type.ConstructorInterceptor;
import com.ancient.common.rule.TagManager;

public class RocketMQPushConsumerMethodInterceptor implements ConstructorInterceptor {

    @Override
    public void onConstructor(Object obj, Object[] allArguments) {
        String consumerGroupName = (String) allArguments[1];
        consumerGroupName += TagManager.getTag();
        allArguments[1] = consumerGroupName;
    }

}
