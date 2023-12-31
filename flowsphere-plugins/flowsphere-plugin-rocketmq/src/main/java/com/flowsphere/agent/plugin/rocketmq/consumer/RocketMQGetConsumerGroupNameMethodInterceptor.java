package com.flowsphere.agent.plugin.rocketmq.consumer;


import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.common.rule.TagManager;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQGetConsumerGroupNameMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        try {
            String consumerGroupName = (String) callable.call();
            consumerGroupName += TagManager.getTag();
            instantMethodInterceptorResult.setContinue(false);
            instantMethodInterceptorResult.setResult(consumerGroupName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }
}
