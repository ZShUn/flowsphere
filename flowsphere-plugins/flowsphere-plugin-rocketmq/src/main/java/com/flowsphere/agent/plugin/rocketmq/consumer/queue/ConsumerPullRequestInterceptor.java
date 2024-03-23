package com.flowsphere.agent.plugin.rocketmq.consumer.queue;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import com.flowsphere.agent.plugin.rocketmq.ModelType;
import org.apache.rocketmq.client.impl.consumer.PullRequest;
import org.apache.rocketmq.common.message.MessageQueue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ConsumerPullRequestInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        PluginConfig pluginConfig = PluginConfigCache.get();
        RocketMQConfig rocketMQConfig = pluginConfig.getRocketMQConfig();
        if (!ModelType.QUEUE.getModelType().equals(rocketMQConfig.getModelType())) {
            return;
        }
        if (allArguments[0] instanceof PullRequest) {
            PullRequest pullRequest = (PullRequest) allArguments[0];

            MessageQueue messageQueue = pullRequest.getMessageQueue();

            List<RocketMQConfig.ConsumerConfig> consumerConfigList = rocketMQConfig.getConsumerConfigList();

            boolean isGray = consumerConfigList.stream().filter(
                            consumerGroupConfig ->
                                    consumerGroupConfig.getConsumerGroupName().equals(pullRequest.getConsumerGroup()))
                    .findFirst()
                    .map(RocketMQConfig.ConsumerConfig::getQueueList)
                    .orElse(new ArrayList<>())
                    .stream()
                    .anyMatch(queueId -> queueId == messageQueue.getQueueId());
            instantMethodInterceptorResult.setContinue(isGray);

        }
    }

}
