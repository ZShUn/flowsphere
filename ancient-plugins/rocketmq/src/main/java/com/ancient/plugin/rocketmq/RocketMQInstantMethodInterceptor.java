package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.InstantMethodInterceptor;
import com.ancient.agent.core.interceptor.InstantInterceptorResult;
import com.ancient.plugin.rocketmq.enhance.ConsumerPullRequestEnhance;
import com.ancient.plugin.rocketmq.enhance.ProducerMessageQueueEnhance;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQInstantMethodInterceptor implements InstantMethodInterceptor {

    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantInterceptorResult instantInterceptorResult) {
        if (RocketMQInterceptorManager.isInterceptorMethod(method.getName())) {
            ProducerMessageQueueEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, instantInterceptorResult);
            ConsumerPullRequestEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, instantInterceptorResult);
        }
    }

    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantInterceptorResult instantInterceptorResult) {

    }

    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
