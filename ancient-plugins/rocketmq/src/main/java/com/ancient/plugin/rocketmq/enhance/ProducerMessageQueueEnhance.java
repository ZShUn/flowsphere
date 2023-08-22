package com.ancient.plugin.rocketmq.enhance;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.enhance.InstanceEnhance;
import com.ancient.agent.core.interceptor.InstantInterceptorResult;
import com.ancient.common.cache.RuleCache;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.entity.ProducerEntity;
import com.ancient.common.entity.RuleEntity;
import org.apache.rocketmq.client.impl.producer.TopicPublishInfo;
import org.apache.rocketmq.common.message.MessageQueue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class ProducerMessageQueueEnhance implements InstanceEnhance {

    private static final ProducerMessageQueueEnhance INSTANCE = new ProducerMessageQueueEnhance();

    public static ProducerMessageQueueEnhance getInstance() {
        return INSTANCE;
    }

    public void enhance(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantInterceptorResult instantInterceptorResult) {
        if (allArguments[0] instanceof TopicPublishInfo) {
            TopicPublishInfo topicPublishInfo = (TopicPublishInfo) allArguments[0];
            RuleEntity ruleEntity = RuleCache.get(CommonConstant.RULE_KEY);
            if (Objects.nonNull(ruleEntity) && Objects.nonNull(ruleEntity.getRocketMQEntity())) {
                List<MessageQueue> messageQueueList = new ArrayList<>();
                for (MessageQueue messageQueue : topicPublishInfo.getMessageQueueList()) {
                    for (ProducerEntity producerEntity : ruleEntity.getRocketMQEntity().getProducerEntityList()) {
                        if (ruleEntity.getRocketMQEntity().getTopic().equals(messageQueue.getTopic())
                                && producerEntity.getQueueList().contains(messageQueue.getQueueId())) {
                            for (Integer queueId : producerEntity.getQueueList()) {
                                if (messageQueue.getQueueId() == queueId) {
                                    messageQueueList.add(messageQueue);
                                }
                            }
                        }
                    }
                }
                topicPublishInfo.setMessageQueueList(messageQueueList);
            }
        }

    }
}
