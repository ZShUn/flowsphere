package com.flowsphere.plugin.rocketmq.consumer;

import com.flowsphere.agent.core.interceptor.type.StaticMethodInterceptor;
import com.flowsphere.plugin.rocketmq.consumer.expression.ConsumerMetadata;

import java.lang.reflect.Method;

public class RocketMQBuildMethodInterceptor extends AbstractRocketMQConsumerExpressionEnhanceInterceptor implements StaticMethodInterceptor {


    @Override
    protected ConsumerMetadata generatorConsumerMetadata(Object[] args) {
        String topic = (String) args[0];
        String subString = (String) args[1];
        String expressionType = (String) args[2];
        return new ConsumerMetadata(topic, subString, expressionType);
    }

    @Override
    public void afterMethod(Class<?> clazz, Method method, Object[] args, Object result) {

    }

    @Override
    public void exceptionMethod(Class<?> clazz, Method method, Object[] args, Throwable throwable) {

    }

}
