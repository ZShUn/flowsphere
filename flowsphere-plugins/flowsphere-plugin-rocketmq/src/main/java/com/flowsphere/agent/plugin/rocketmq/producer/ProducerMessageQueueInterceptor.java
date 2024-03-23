package com.flowsphere.agent.plugin.rocketmq.producer;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import com.flowsphere.agent.plugin.rocketmq.ModelType;
import org.apache.rocketmq.client.impl.producer.TopicPublishInfo;
import org.apache.rocketmq.common.message.MessageQueue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class ProducerMessageQueueInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        PluginConfig pluginConfig = PluginConfigCache.get();
        String modelType = pluginConfig.getRocketMQConfig().getModelType();
        if (!ModelType.QUEUE.getModelType().equals(modelType)) {
            return;
        }
        if (allArguments[0] instanceof TopicPublishInfo) {
            TopicPublishInfo topicPublishInfo = (TopicPublishInfo) allArguments[0];
            RocketMQConfig.ProducerConfig producerConfig = filterProducerConfig(topicPublishInfo, pluginConfig.getRocketMQConfig());
            if (Objects.isNull(producerConfig)) {
                return;
            }
            List<MessageQueue> messageQueueList = new ArrayList<>();
            for (MessageQueue messageQueue : topicPublishInfo.getMessageQueueList()) {
                for (Integer queueId : producerConfig.getQueueIdList()) {
                    if (messageQueue.getQueueId() == queueId) {
                        messageQueueList.add(messageQueue);
                    }
                }

            }
            topicPublishInfo.setMessageQueueList(messageQueueList);
        }
    }

    private RocketMQConfig.ProducerConfig filterProducerConfig(TopicPublishInfo topicPublishInfo, RocketMQConfig rocketMQConfig) {
        MessageQueue messageQueue = topicPublishInfo.getMessageQueueList().get(0);
        List<RocketMQConfig.ProducerConfig> producerConfigList = rocketMQConfig.getProducerConfigList();
        for (RocketMQConfig.ProducerConfig producerConfig : producerConfigList) {
            if (producerConfig.getTopic().equals(messageQueue.getTopic())) {
                return producerConfig;
            }
        }
        return null;
    }

}
