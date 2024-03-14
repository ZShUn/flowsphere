package com.flowsphere.agent.plugin.rocketmq.consumer.sql;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
import com.flowsphere.agent.plugin.rocketmq.constant.RocketMQConstant;
import com.flowsphere.agent.plugin.rocketmq.consumer.config.ConsumerGroupConfig;
import com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression.ConsumerMetadata;
import com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression.ExpressionTypeEnum;
import com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression.SQL92Enhance;
import com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression.SQL92EnhanceManager;
import com.flowsphere.common.rule.context.TagContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.protocol.heartbeat.ConsumerData;
import org.apache.rocketmq.common.protocol.heartbeat.HeartbeatData;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@Slf4j
public class SendHearbeatInterceptor extends AbstractSqlInterceptor implements InstantMethodInterceptor {

    @Override
    protected void doBeforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        HeartbeatData heartbeatData = (HeartbeatData) allArguments[1];
        for (ConsumerData consumerData : heartbeatData.getConsumerDataSet()) {
            List<ConsumerGroupConfig> consumerGroupConfigs = (List<ConsumerGroupConfig>) PluginConfigManager.getConfig(RocketMQConstant.ROCKETMQ, RocketMQConstant.CONSUMER_GROUP_CONFIG);
            if (log.isDebugEnabled()) {
                log.debug("[FlowSphere] SendHearbeatInterceptor rocketMQ groupName={} consumerGroupConfigs={}", consumerData.getGroupName(), consumerGroupConfigs);
            }
            boolean consumerGroupMatchResult = consumerGroupConfigs.stream().anyMatch(consumerGroupConfig ->
                    consumerGroupConfig.getConsumerGroupName().equals(consumerData.getGroupName()));
            if (consumerGroupMatchResult) {
                enhanceSQL92(consumerData);
            }
        }
    }


    private void enhanceSQL92(ConsumerData consumerData) {
        Set<SubscriptionData> result = new HashSet<>();
        for (SubscriptionData oldSubscriptionData : consumerData.getSubscriptionDataSet()) {
            if (oldSubscriptionData.getTopic().contains("%RETRY%")) {
                result.add(oldSubscriptionData);
                continue;
            }
            SubscriptionData newSubscriptionData = rewriteSQL92(oldSubscriptionData);
            if (log.isDebugEnabled()) {
                log.debug("[FlowSphere] SendHearbeatInterceptor rocketMQ newSubscriptionData={}", newSubscriptionData);
            }
            result.add(newSubscriptionData);
        }
        consumerData.setSubscriptionDataSet(result);
    }

    private SubscriptionData rewriteSQL92(SubscriptionData oldSubscriptionData) {
        ConsumerMetadata consumerMetadata = new ConsumerMetadata();
        consumerMetadata.setTopic(oldSubscriptionData.getTopic());
        consumerMetadata.setSubString(oldSubscriptionData.getSubString());
        consumerMetadata.setExpressionType(oldSubscriptionData.getExpressionType());
        SQL92Enhance sql92Enhance = null;
        if (oldSubscriptionData.getSubString().equals("*")) {
            sql92Enhance = SQL92EnhanceManager.getSQL92Enhance(ExpressionTypeEnum.ALL.getValue());
        } else {
            sql92Enhance = SQL92EnhanceManager.getSQL92Enhance(consumerMetadata.getExpressionType());
        }
        return sql92Enhance.enhance(consumerMetadata);
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
        TagContext.remove();
    }


}
