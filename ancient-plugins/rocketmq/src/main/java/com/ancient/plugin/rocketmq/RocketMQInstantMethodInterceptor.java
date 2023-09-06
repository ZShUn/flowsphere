package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.plugin.rocketmq.enhance.ConsumerPullRequestEnhance;
import com.ancient.plugin.rocketmq.enhance.ProducerMessageQueueEnhance;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        ProducerMessageQueueEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, instantMethodInterceptorResult);
        ConsumerPullRequestEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, instantMethodInterceptorResult);
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
