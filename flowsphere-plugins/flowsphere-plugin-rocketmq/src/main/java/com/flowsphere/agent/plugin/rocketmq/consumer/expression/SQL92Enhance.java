package com.flowsphere.agent.plugin.rocketmq.consumer.expression;

import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

public interface SQL92Enhance {

    SubscriptionData enhance(ConsumerMetadata consumerMetadata);

    ExpressionTypeEnum getOriginExpressionType();


}
