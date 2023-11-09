package com.flowsphere.plugin.rocketmq.consumer;

import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.StaticMethodInterceptor;
import com.flowsphere.plugin.rocketmq.consumer.expression.ConsumerMetadata;
import com.flowsphere.plugin.rocketmq.consumer.expression.SQL92Enhance;
import com.flowsphere.plugin.rocketmq.consumer.expression.SQL92EnhanceManager;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public abstract class AbstractRocketMQConsumerExpressionEnhanceInterceptor implements StaticMethodInterceptor {

    @Override
    public void beforeMethod(Class<?> clazz, Method method, Object[] args, Callable<?> callable, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        ConsumerMetadata consumerMetadata = generatorConsumerMetadata(args);
        if (consumerMetadata.getTopic().contains("%RETRY%")) {
            return;
        }
        SQL92Enhance sql92Enhance = SQL92EnhanceManager.getSQL92Enhance(consumerMetadata.getExpressionType());
        SubscriptionData subscriptionData = sql92Enhance.enhance(consumerMetadata);
        instantMethodInterceptorResult.setContinue(false);
        instantMethodInterceptorResult.setResult(subscriptionData);
    }


    protected abstract ConsumerMetadata generatorConsumerMetadata(Object[] args);


}
