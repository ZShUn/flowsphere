package com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression;

import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

public class SimpleAllEnhance implements SQL92Enhance {

    @Override
    public SubscriptionData enhance(ConsumerMetadata consumerMetadata) {
        SubscriptionData subscriptionData = new SubscriptionData();
        subscriptionData.setTopic(consumerMetadata.getTopic());
        subscriptionData.setExpressionType(ExpressionType.SQL92);
        RocketMQConfig.ConsumerConfig consumerConfig = consumerMetadata.getConsumerConfig();

        StringBuffer tagExpression = new StringBuffer();
        tagExpression
                .append("(TAGS is not null and TAGS in ('*')) and (flowSphereTag is not null and flowSphereTag in (")
                .append(consumerConfig.getTags())
                .append("))");

        subscriptionData.setSubString(tagExpression.toString());
        return subscriptionData;
    }

    @Override
    public ExpressionTypeEnum getOriginExpressionType() {
        return ExpressionTypeEnum.ALL;
    }

}
