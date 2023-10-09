package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.interceptor.type.StaticMethodInterceptor;
import com.ancient.plugin.rocketmq.consumer.ConsumerMetadata;
import com.ancient.plugin.rocketmq.consumer.ExpressionTypeEnum;

import java.lang.reflect.Method;

public class RocketMQBuildSubscriptionDataMethodInterceptor extends AbstractRocketMQConsumerExpressionEnhanceInterceptor implements StaticMethodInterceptor {

    @Override
    protected ConsumerMetadata generatorConsumerMetadata(Object[] args) {
        String topic = (String) args[1];
        String subString = (String) args[2];
        if (subString.equals("*")) {
            return new ConsumerMetadata(topic, subString, ExpressionTypeEnum.ALL.getValue());
        }
        return new ConsumerMetadata(topic, subString, ExpressionTypeEnum.TAG.getValue());
    }

    @Override
    public void afterMethod(Class<?> clazz, Method method, Object[] args, Object result) {

    }

    @Override
    public void exceptionMethod(Class<?> clazz, Method method, Object[] args, Throwable throwable) {

    }

}
