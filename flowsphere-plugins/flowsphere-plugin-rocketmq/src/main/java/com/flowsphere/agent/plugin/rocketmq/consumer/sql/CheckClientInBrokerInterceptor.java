package com.flowsphere.agent.plugin.rocketmq.consumer.sql;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression.ConsumerMetadata;
import com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression.SQL92Enhance;
import com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression.SQL92EnhanceManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * SQL92模式下此类有用
 */
@Slf4j
public class CheckClientInBrokerInterceptor extends AbstractSqlInterceptor implements InstantMethodInterceptor {

    @SneakyThrows
    @Override
    protected void doBeforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        RocketMQConfig.ConsumerConfig currentConsumerConfig = getCurrentConsumerConfig(allArguments);
        if (Objects.isNull(currentConsumerConfig)) {
            return;
        }
        Set<SubscriptionData> subscriptionDataSet = (Set<SubscriptionData>) allArguments[3];
        Set<SubscriptionData> result = new HashSet<>();
        for (SubscriptionData oldSubscriptionData : subscriptionDataSet) {
            if (!oldSubscriptionData.getSubString().equals("*") && oldSubscriptionData.getExpressionType().equals(ExpressionType.SQL92)) {
                ConsumerMetadata consumerMetadata = buildConsumerMetadata(oldSubscriptionData, currentConsumerConfig);
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

    private RocketMQConfig.ConsumerConfig getCurrentConsumerConfig(Object[] allArguments) {
        String groupName = (String) allArguments[1];
        PluginConfig pluginConfig = PluginConfigCache.get();
        RocketMQConfig.ConsumerConfig consumerGroupConfigFilterResult = pluginConfig.getRocketMQConfig()
                .getConsumerConfigList()
                .stream()
                .filter(consumerGroupConfig -> consumerGroupConfig.getConsumerGroupName().equals(groupName))
                .findFirst()
                .orElse(null);
        return consumerGroupConfigFilterResult;
    }

    private ConsumerMetadata buildConsumerMetadata(SubscriptionData oldSubscriptionData, RocketMQConfig.ConsumerConfig consumerConfig) {
        ConsumerMetadata consumerMetadata = new ConsumerMetadata();
        consumerMetadata.setTopic(oldSubscriptionData.getTopic());
        consumerMetadata.setSubString(oldSubscriptionData.getSubString());
        consumerMetadata.setExpressionType(oldSubscriptionData.getExpressionType());
        consumerMetadata.setConsumerConfig(consumerConfig);
        return consumerMetadata;
    }


}
