package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.MethodInterceptor;
import com.ancient.agent.core.interceptor.MethodInterceptorResult;
import com.ancient.plugin.rocketmq.enhance.ConsumerPullRequestEnhance;
import com.ancient.plugin.rocketmq.enhance.ProducerMessageQueueEnhance;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQMethodInterceptor implements MethodInterceptor {

    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, MethodInterceptorResult methodInterceptorResult) {
        if (RocketMQInterceptorManager.isInterceptorMethod(method.getName())) {
            ProducerMessageQueueEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, methodInterceptorResult);
            ConsumerPullRequestEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, methodInterceptorResult);
        }
    }

    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, MethodInterceptorResult methodInterceptorResult) {

    }

    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
