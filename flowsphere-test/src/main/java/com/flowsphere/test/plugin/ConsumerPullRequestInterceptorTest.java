package com.flowsphere.test.plugin;

import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import com.flowsphere.agent.plugin.rocketmq.ModelType;
import com.flowsphere.agent.plugin.rocketmq.consumer.queue.ConsumerPullRequestInterceptor;
import com.google.common.collect.Lists;
import org.apache.rocketmq.client.impl.consumer.PullRequest;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConsumerPullRequestInterceptorTest {

    @Test
    public void beforeResultIsContinueTrueTest() {

        InstantMethodInterceptorResult result = before("test_consumer_group","test_consumer_group",1,1);

        Assertions.assertTrue(result.isContinue());
    }

    @Test
    public void beforeQueueResultIsContinueFalseTest() {

        InstantMethodInterceptorResult result = before("test_consumer_group","test_consumer_group",1,2);

        Assertions.assertTrue(!result.isContinue());
    }

    @Test
    public void beforeConsumerGroupNameIsContinueFalseTest() {

        InstantMethodInterceptorResult result = before("test_consumer_group1","test_consumer_group",1,1);

        Assertions.assertTrue(!result.isContinue());
    }


    @Test
    public InstantMethodInterceptorResult before(String groupName, String pullGroupName, int queueId, int messageQueueId) {
        ConsumerPullRequestInterceptor interceptor = new ConsumerPullRequestInterceptor();
        PluginConfig pluginConfig = new PluginConfig();
        RocketMQConfig rocketMQConfig = new RocketMQConfig();
        rocketMQConfig.setModelType(ModelType.QUEUE.getModelType());

        RocketMQConfig.ConsumerConfig consumerConfig = new RocketMQConfig.ConsumerConfig();
        consumerConfig.setConsumerGroupName(groupName);
        consumerConfig.setQueueList(Lists.newArrayList(queueId));
        rocketMQConfig.setConsumerConfigList(Lists.newArrayList(consumerConfig));
        pluginConfig.setRocketMQConfig(rocketMQConfig);

        PluginConfigCache.put(pluginConfig);

        PullRequest pullRequest = new PullRequest();
        pullRequest.setConsumerGroup(pullGroupName);

        MessageQueue messageQueue = new MessageQueue();
        messageQueue.setQueueId(messageQueueId);
        pullRequest.setMessageQueue(messageQueue);

        Object[] objects = new Object[]{pullRequest};

        InstantMethodInterceptorResult result = new InstantMethodInterceptorResult();

        interceptor.beforeMethod(null, objects, null, null, result);

        return result;
    }


}
