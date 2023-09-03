package com.ancient.plugin.rocketmq.enhance;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.enhance.InstanceEnhance;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptorResult;
import com.ancient.common.cache.RuleCache;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.entity.ConsumerEntity;
import com.ancient.common.entity.RuleEntity;
import org.apache.rocketmq.client.impl.consumer.PullRequest;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

public class ConsumerPullRequestEnhance implements InstanceEnhance {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPullRequestEnhance.class);

    private static final ConsumerPullRequestEnhance INSTANCE = new ConsumerPullRequestEnhance();

    public static ConsumerPullRequestEnhance getInstance() {
        return INSTANCE;
    }

    public void enhance(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (allArguments[0] instanceof PullRequest) {
            PullRequest pullRequest = (PullRequest) allArguments[0];

            MessageQueue messageQueue = pullRequest.getMessageQueue();

            String topic = messageQueue.getTopic();

            RuleEntity ruleEntity = RuleCache.get(CommonConstant.RULE_KEY);

            if (Objects.nonNull(ruleEntity)
                    && Objects.nonNull(ruleEntity.getRocketMQEntity())
                    && topic.equals(ruleEntity.getRocketMQEntity().getTopic())) {
                for (ConsumerEntity consumerEntity : ruleEntity.getRocketMQEntity().getConsumerEntityList()) {
                    if (consumerEntity.getGroupName().equals(pullRequest.getConsumerGroup())
                            && consumerEntity.getQueueList().contains(messageQueue.getQueueId())) {
                        instantMethodInterceptorResult.setContinue(true);
                        return;
                    }
                }
            }
            instantMethodInterceptorResult.setContinue(false);
        }
    }
}
