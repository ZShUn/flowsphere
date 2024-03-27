package com.flowsphere.test.plugin;

import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import org.apache.rocketmq.client.common.ThreadLocalIndex;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;
import com.google.common.collect.Lists;

import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import com.flowsphere.agent.plugin.rocketmq.ModelType;
import com.flowsphere.agent.plugin.rocketmq.producer.ProducerMessageQueueInterceptor;
import org.apache.rocketmq.client.impl.producer.TopicPublishInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ProducerMessageQueueInterceptorTest {

    @Test
    public void beforeTest() {
        PluginConfig pluginConfig = new PluginConfig();
        RocketMQConfig rocketMQConfig = new RocketMQConfig();
        rocketMQConfig.setModelType(ModelType.QUEUE.getModelType());

        List<RocketMQConfig.ProducerConfig> producerConfigList = new ArrayList<>();
        RocketMQConfig.ProducerConfig producerConfig = new RocketMQConfig.ProducerConfig();
        producerConfig.setTopic("producer_topic");
        producerConfig.setQueueIdList(Lists.newArrayList(0, 1));


        producerConfigList.add(producerConfig);
        rocketMQConfig.setProducerConfigList(producerConfigList);
        pluginConfig.setRocketMQConfig(rocketMQConfig);

        PluginConfigCache.put(pluginConfig);

        TopicPublishInfo topicPublishInfo = new TopicPublishInfo();
        topicPublishInfo.setOrderTopic(false);
        MessageQueue messageQueue = new MessageQueue();
        messageQueue.setTopic("producer_topic");
        messageQueue.setBrokerName("producer_broker");
        messageQueue.setQueueId(0);

        topicPublishInfo.setMessageQueueList(Lists.newArrayList(messageQueue));
        topicPublishInfo.setSendWhichQueue(new ThreadLocalIndex());
        topicPublishInfo.setHaveTopicRouterInfo(false);
        topicPublishInfo.setTopicRouteData(new TopicRouteData());

        Object[] objects = new Object[]{topicPublishInfo};

        InstantMethodInterceptorResult result = new InstantMethodInterceptorResult();
        ProducerMessageQueueInterceptor interceptor = new ProducerMessageQueueInterceptor();

        interceptor.beforeMethod(null, objects, null, null, result);

        Assertions.assertTrue(topicPublishInfo.getMessageQueueList().size() == 1);
    }

}
