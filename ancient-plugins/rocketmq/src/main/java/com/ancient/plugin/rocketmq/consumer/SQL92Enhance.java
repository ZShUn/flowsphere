package com.ancient.plugin.rocketmq.consumer;

import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

public interface SQL92Enhance {

    SubscriptionData enhance(ConsumerMetadata consumerMetadata);

    ExpressionTypeEnum getOriginExpressionType();


}
