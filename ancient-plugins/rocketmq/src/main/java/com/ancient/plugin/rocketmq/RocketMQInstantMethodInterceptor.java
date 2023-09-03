package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptorResult;
import com.ancient.plugin.rocketmq.enhance.ConsumerPullRequestEnhance;
import com.ancient.plugin.rocketmq.enhance.ProducerMessageQueueEnhance;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQInstantMethodInterceptor implements InstantMethodInterceptor {

    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
//        if (RocketMQInterceptorManager.isInterceptorMethod(method.getName())) {
            ProducerMessageQueueEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, instantMethodInterceptorResult);
            ConsumerPullRequestEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, instantMethodInterceptorResult);
//        }
    }

    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantMethodInterceptorResult instantMethodInterceptorResult) {

    }

    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
