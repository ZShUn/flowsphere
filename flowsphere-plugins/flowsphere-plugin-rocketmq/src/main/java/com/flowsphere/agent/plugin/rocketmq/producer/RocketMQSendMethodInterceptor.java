package com.flowsphere.agent.plugin.rocketmq.producer;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
import com.flowsphere.agent.plugin.rocketmq.ModelType;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.tag.TagManager;
import org.apache.rocketmq.common.message.Message;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQSendMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        PluginConfig pluginConfig = PluginConfigManager.getPluginConfig();
        String modelType = pluginConfig.getRocketMQConfig().getModelType();
        if (!ModelType.SQL92.getModelType().equals(modelType)) {
            return;
        }
        Message message = (Message) allArguments[0];
        message.putUserProperty(CommonConstant.TAG, TagManager.getTag());
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
