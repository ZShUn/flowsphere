package com.flowsphere.test.plugin.rocketmq;

import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import com.flowsphere.agent.plugin.rocketmq.ModelType;
import com.flowsphere.agent.plugin.rocketmq.consumer.sql.SendHeartbeatInterceptor;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.*;
import org.junit.jupiter.api.Test;

public class SendHeartbeatInterceptorTest {

    @Test
    public void beforeRetryTest() {

        HeartbeatData heartbeatData = before("tagA", "%RETRY%_sql92_topic", "*", "TAG");

        for (ConsumerData newConsumerData : heartbeatData.getConsumerDataSet()) {
            for (SubscriptionData newSubscriptionData : newConsumerData.getSubscriptionDataSet()) {
                if (!newSubscriptionData.getSubString().equals("*") || !newSubscriptionData.getExpressionType().equals("TAG")) {
                    throw new RuntimeException("send heartbeat execute beforeRetryTest error");
                }
            }
        }

    }

    @Test
    public void beforeAllTest() {

        HeartbeatData heartbeatData = before("tagA", "sql92_topic", "*", "TAG");

        for (ConsumerData newConsumerData : heartbeatData.getConsumerDataSet()) {
            for (SubscriptionData newSubscriptionData : newConsumerData.getSubscriptionDataSet()) {
                if (!newSubscriptionData.getExpressionType().equals("SQL92") ||
                        !newSubscriptionData.getSubString().equals("(TAGS is not null and TAGS in ('*')) and (flowSphereTag is not null and flowSphereTag in (tagA))")) {
                    throw new RuntimeException("send heartbeat execute beforeAllTest error");
                }
            }
        }
    }

    @Test
    public void beforeTagTest() {

        HeartbeatData heartbeatData = before("tagA", "sql92_topic", "tagB", "TAG");

        for (ConsumerData newConsumerData : heartbeatData.getConsumerDataSet()) {
            for (SubscriptionData newSubscriptionData : newConsumerData.getSubscriptionDataSet()) {
                if (!newSubscriptionData.getExpressionType().equals("SQL92") ||
                        !newSubscriptionData.getSubString().equals("(TAGS is not null and TAGS in ('tagB')) and (flowSphereTag is not null and flowSphereTag in (tagA))")) {
                    throw new RuntimeException("send heartbeat execute beforeTagTest error");
                }
            }
        }
    }

    @Test
    public void beforeSql92Test() {
        HeartbeatData heartbeatData = before("tagA", "sql92_topic", "TAGS in ('*')", "SQL92");

        for (ConsumerData newConsumerData : heartbeatData.getConsumerDataSet()) {
            for (SubscriptionData newSubscriptionData : newConsumerData.getSubscriptionDataSet()) {
                if (!newSubscriptionData.getExpressionType().equals("SQL92") ||
                        !newSubscriptionData.getSubString().equals("TAGS in ('*') and (flowSphereTag is not null and flowSphereTag in (tagA))")) {
                    throw new RuntimeException("send heartbeat execute beforeSql92Test error");
                }
            }
        }
    }


    private HeartbeatData before(String consumerConfigTags, String topic, String subString, String expressionType) {
        PluginConfig pluginConfig = new PluginConfig();
        RocketMQConfig rocketMQConfig = new RocketMQConfig();
        rocketMQConfig.setModelType(ModelType.SQL92.getModelType());

        RocketMQConfig.ConsumerConfig consumerConfig = new RocketMQConfig.ConsumerConfig();
        consumerConfig.setConsumerGroupName("sql92_consumer_group_name");
        consumerConfig.setQueueList(Lists.newArrayList(1));
        consumerConfig.setTags(consumerConfigTags);
        rocketMQConfig.setConsumerConfigList(Lists.newArrayList(consumerConfig));
        pluginConfig.setRocketMQConfig(rocketMQConfig);

        PluginConfigCache.put(pluginConfig);

        HeartbeatData heartbeatData = new HeartbeatData();
        ConsumerData consumerData = new ConsumerData();
        consumerData.setGroupName("sql92_consumer_group_name");
        consumerData.setConsumeType(ConsumeType.CONSUME_ACTIVELY);
        consumerData.setMessageModel(MessageModel.CLUSTERING);
        consumerData.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        SubscriptionData subscriptionData = new SubscriptionData();
        subscriptionData.setTopic(topic);
        subscriptionData.setSubString(subString);
        subscriptionData.setTagsSet(Sets.newHashSet());
        subscriptionData.setSubVersion(0L);
        subscriptionData.setCodeSet(Sets.newHashSet());
        subscriptionData.setClassFilterMode(false);
        subscriptionData.setExpressionType(expressionType);


        consumerData.setSubscriptionDataSet(Sets.newHashSet(subscriptionData));
        consumerData.setUnitMode(false);

        heartbeatData.setConsumerDataSet(Sets.newHashSet(consumerData));
        Object[] objects = new Object[2];
        objects[1] = heartbeatData;

        SendHeartbeatInterceptor interceptor = new SendHeartbeatInterceptor();
        InstantMethodInterceptorResult result = new InstantMethodInterceptorResult();
        interceptor.beforeMethod(null, objects, null, null, result);
        return heartbeatData;
    }

}
