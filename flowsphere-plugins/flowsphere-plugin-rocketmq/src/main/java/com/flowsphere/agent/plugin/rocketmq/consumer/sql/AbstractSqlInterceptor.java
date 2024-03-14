package com.flowsphere.agent.plugin.rocketmq.consumer.sql;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.plugin.rocketmq.ModelType;
import com.flowsphere.agent.plugin.rocketmq.RocketMQConfigManager;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public abstract class AbstractSqlInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {

        String modelType = RocketMQConfigManager.getModelType();
        if (!ModelType.SQL92.getModelType().equals(modelType)) {
            return;
        }
        doBeforeMethod(customContextAccessor, allArguments, callable, method, instantMethodInterceptorResult);
    }

    protected abstract void doBeforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult);

}
