package com.ancient.plugin.rocketmq.consumer.expression;

import com.ancient.plugin.rocketmq.consumer.expression.ConsumerMetadata;
import com.ancient.plugin.rocketmq.consumer.expression.ExpressionTypeEnum;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

public interface SQL92Enhance {

    SubscriptionData enhance(ConsumerMetadata consumerMetadata);

    ExpressionTypeEnum getOriginExpressionType();


}
