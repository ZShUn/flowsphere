package com.flowsphere.agent.plugin.rocketmq.consumer.sql;


import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.common.tag.TagManager;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@Slf4j
public class RocketMQGetConsumerGroupNameMethodInterceptor extends AbstractSqlInterceptor implements InstantMethodInterceptor {

    @Override
    protected void doBeforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        try {
            String consumerGroupName = (String) callable.call();
            consumerGroupName += TagManager.getTag();
            if (log.isDebugEnabled()) {
                log.debug("[FlowSphere] RocketMQGetConsumerGroupNameMethodInterceptor rocketMQ consumerGroupName={}", consumerGroupName);
            }
            instantMethodInterceptorResult.setContinue(false);
            instantMethodInterceptorResult.setResult(consumerGroupName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
