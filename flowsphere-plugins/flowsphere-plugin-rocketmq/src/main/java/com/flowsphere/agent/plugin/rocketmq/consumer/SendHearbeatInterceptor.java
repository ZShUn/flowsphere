package com.flowsphere.agent.plugin.rocketmq.consumer;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.ConsumerMetadata;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.SQL92Enhance;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.SQL92EnhanceManager;
import com.flowsphere.common.rule.context.TagContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.protocol.heartbeat.ConsumerData;
import org.apache.rocketmq.common.protocol.heartbeat.HeartbeatData;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

@Slf4j
public class SendHearbeatInterceptor implements InstantMethodInterceptor {

    //org.apache.rocketmq.client.impl.consumer.PullAPIWrapper.processPullResult
    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        HeartbeatData heartbeatData = (HeartbeatData) allArguments[1];
        for (ConsumerData consumerData : heartbeatData.getConsumerDataSet()) {
            Set<SubscriptionData> result = new HashSet<>();
            for (SubscriptionData oldSubscriptionData : consumerData.getSubscriptionDataSet()) {
                ConsumerMetadata consumerMetadata = new ConsumerMetadata();
                consumerMetadata.setTopic(oldSubscriptionData.getTopic());
                consumerMetadata.setSubString(oldSubscriptionData.getSubString());
                consumerMetadata.setExpressionType(oldSubscriptionData.getExpressionType());

                if (consumerMetadata.getTopic().contains("%RETRY%")) {
                    result.add(oldSubscriptionData);
                    continue;
                }
                SQL92Enhance sql92Enhance = SQL92EnhanceManager.getSQL92Enhance(consumerMetadata.getExpressionType());
                SubscriptionData newSubscriptionData = sql92Enhance.enhance(consumerMetadata);
                result.add(newSubscriptionData);
            }
            consumerData.setSubscriptionDataSet(result);
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
        TagContext.remove();
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
