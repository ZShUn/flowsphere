package com.flowsphere.agent.plugin.rocketmq.consumer;

import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.StaticMethodInterceptor;
import com.flowsphere.agent.plugin.rocketmq.config.RocketMQConfigManager;
import com.flowsphere.agent.plugin.rocketmq.constant.RocketMQConstant;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.ConsumerMetadata;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.SQL92Enhance;
import com.flowsphere.agent.plugin.rocketmq.consumer.expression.SQL92EnhanceManager;
import com.flowsphere.common.util.JacksonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class AbstractRocketMQConsumerExpressionEnhanceInterceptor implements StaticMethodInterceptor {

    @Override
    public void beforeMethod(Class<?> clazz, Method method, Object[] args, Callable<?> callable, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        //TODO 特殊场景不需要带tag消费，否则会拉取不到消息
        //TODO 需要读取本地配置


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


    private List<String> getBlackList() {
        String blackListStr = RocketMQConfigManager.getConfig(RocketMQConstant.ROCKETMQ_CONSUMER_BLACK_LIST);
        if (StringUtils.isBlank(blackListStr)) {
            return null;
        }
        List<String> blackList = JacksonUtils.toList(blackListStr, String.class);
        return blackList;
    }

    private boolean isBlack() {
        return false;
    }

}
