package com.flowsphere.agent.plugin.rocketmq.consumer;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
import com.flowsphere.agent.plugin.rocketmq.constant.RocketMQConstant;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.ConsumerMetadata;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.ExpressionTypeEnum;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.SQL92Enhance;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.SQL92EnhanceManager;
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
public class SendHearbeatInterceptor implements InstantMethodInterceptor {

    //org.apache.rocketmq.client.impl.consumer.PullAPIWrapper.processPullResult
    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        HeartbeatData heartbeatData = (HeartbeatData) allArguments[1];
        for (ConsumerData consumerData : heartbeatData.getConsumerDataSet()) {
            String groupName = consumerData.getGroupName();
            List<String> groupNameList = (List<String>) PluginConfigManager.getConfig(RocketMQConstant.ROCKETMQ, RocketMQConstant.ROCKETMQ_CONSUMER_BLACK_LIST);
            if (groupNameList.contains(groupName)) {
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

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
