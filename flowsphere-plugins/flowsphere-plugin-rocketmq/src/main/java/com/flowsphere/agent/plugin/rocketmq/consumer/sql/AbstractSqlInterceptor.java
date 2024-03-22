package com.flowsphere.agent.plugin.rocketmq.consumer.sql;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import com.flowsphere.agent.plugin.rocketmq.ModelType;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public abstract class AbstractSqlInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        PluginConfig pluginConfig = PluginConfigManager.getPluginConfig();
        RocketMQConfig rocketMQConfig = pluginConfig.getRocketMQConfig();
        if (!ModelType.SQL92.getModelType().equals(rocketMQConfig.getModelType())) {
            return;
        }
        doBeforeMethod(customContextAccessor, allArguments, callable, method, instantMethodInterceptorResult);
    }

    protected abstract void doBeforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult);

}
