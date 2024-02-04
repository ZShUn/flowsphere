package com.flowsphere.agent.plugin.rocketmq.consumer;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
import com.flowsphere.agent.plugin.rocketmq.constant.RocketMQConstant;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.ConsumerMetadata;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.SQL92Enhance;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.SQL92EnhanceManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@Slf4j
public class CheckClientInBrokerInterceptor implements InstantMethodInterceptor {

    @SneakyThrows
    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        String groupName = (String) allArguments[1];
        List<String> groupNameList = (List<String>) PluginConfigManager.getConfig(RocketMQConstant.ROCKETMQ, RocketMQConstant.ROCKETMQ_CONSUMER_BLACK_LIST);
        if (!groupNameList.contains(groupName)) {
            return;
        }
        Set<SubscriptionData> subscriptionDataSet = (Set<SubscriptionData>) allArguments[3];
        Set<SubscriptionData> result = new HashSet<>();
        for (SubscriptionData oldSubscriptionData : subscriptionDataSet) {

            if (!oldSubscriptionData.getSubString().equals("*") && oldSubscriptionData.getExpressionType().equals(ExpressionType.TAG)) {
                ConsumerMetadata consumerMetadata = new ConsumerMetadata();
                consumerMetadata.setTopic(oldSubscriptionData.getTopic());
                consumerMetadata.setSubString(oldSubscriptionData.getSubString());
                consumerMetadata.setExpressionType(oldSubscriptionData.getExpressionType());

                SQL92Enhance sql92Enhance = SQL92EnhanceManager.getSQL92Enhance(consumerMetadata.getExpressionType());
                SubscriptionData newSubscriptionData = sql92Enhance.enhance(consumerMetadata);
                if (log.isDebugEnabled()) {
                    log.debug("[FlowSphere] CheckClientInBrokerInterceptor rocketMQ checkClientInBroker={}", newSubscriptionData);
                }
                result.add(newSubscriptionData);
            }
            if (oldSubscriptionData.getSubString().equals("*") && oldSubscriptionData.getExpressionType().equals(ExpressionType.TAG) || oldSubscriptionData.getTopic().contains("%RETRY%")) {
                result.add(oldSubscriptionData);
            }
        }
        instantMethodInterceptorResult.setContinue(false);
        instantMethodInterceptorResult.setResult(result);

    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
