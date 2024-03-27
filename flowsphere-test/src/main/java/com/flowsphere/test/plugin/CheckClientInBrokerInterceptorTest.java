package com.flowsphere.test.plugin;

import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.google.common.collect.Lists;

import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import com.flowsphere.agent.plugin.rocketmq.consumer.sql.CheckClientInBrokerInterceptor;
import com.google.common.collect.Sets;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class CheckClientInBrokerInterceptorTest {

    @Test
    public void beforeTest() {

        initPluginConfigCache();

        Object[] allArguments = new Object[4];
        allArguments[1] = "test_consumer_group_name";
        allArguments[3] = buildSubscriptionData();

        InstantMethodInterceptorResult result = new InstantMethodInterceptorResult();
        CheckClientInBrokerInterceptor interceptor = new CheckClientInBrokerInterceptor();

        interceptor.beforeMethod(null, allArguments, null, null, result);

        for (SubscriptionData newSubscriptionData : (Set<SubscriptionData>) result.getResult()) {
            if (newSubscriptionData.getTopic().equals("test_topic")) {
                if (!newSubscriptionData.getSubString().equals("TAGS in ('a') and (flowSphereTag is not null and flowSphereTag in (tagA))")) {
                    throw new RuntimeException();
                }
            }
        }

    }

    public void initPluginConfigCache(){
        PluginConfig pluginConfig = new PluginConfig();
        RocketMQConfig.ConsumerConfig consumerGroupConfigFilterResult = new RocketMQConfig.ConsumerConfig();
        consumerGroupConfigFilterResult.setConsumerGroupName("test_consumer_group_name");
        consumerGroupConfigFilterResult.setTags("tagA");
        RocketMQConfig rocketMQConfig = new RocketMQConfig();
        rocketMQConfig.setModelType("sql92");
        rocketMQConfig.setConsumerConfigList(Lists.newArrayList(consumerGroupConfigFilterResult));

        pluginConfig.setRocketMQConfig(rocketMQConfig);

        PluginConfigCache.put(pluginConfig);
    }

    public Set<SubscriptionData> buildSubscriptionData() {

        Set<SubscriptionData> subscriptionDataSet = Sets.newHashSet();

        SubscriptionData originSubscriptionData = new SubscriptionData();
        originSubscriptionData.setFilterClassSource("");
        originSubscriptionData.setTopic("test_topic");
        originSubscriptionData.setSubString("TAGS in ('a')");
        originSubscriptionData.setTagsSet(Sets.newHashSet());
        originSubscriptionData.setSubVersion(0L);
        originSubscriptionData.setCodeSet(Sets.newHashSet());
        originSubscriptionData.setClassFilterMode(false);
        originSubscriptionData.setExpressionType("SQL92");
        subscriptionDataSet.add(originSubscriptionData);

        SubscriptionData retrySubscriptionData = new SubscriptionData();
        retrySubscriptionData.setFilterClassSource("");
        retrySubscriptionData.setTopic("%RETRY%_test_topic");
        retrySubscriptionData.setSubString("*");
        retrySubscriptionData.setTagsSet(Sets.newHashSet());
        retrySubscriptionData.setSubVersion(0L);
        retrySubscriptionData.setCodeSet(Sets.newHashSet());
        retrySubscriptionData.setClassFilterMode(false);
        retrySubscriptionData.setExpressionType("TAG");
        subscriptionDataSet.add(retrySubscriptionData);
        return subscriptionDataSet;
    }

}
