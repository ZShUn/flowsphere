package com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression;

import com.flowsphere.common.tag.TagManager;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

public class SimpleSQL92Enhance implements SQL92Enhance {
    @Override
    public SubscriptionData enhance(ConsumerMetadata consumerMetadata) {
        SubscriptionData subscriptionData = new SubscriptionData();
        subscriptionData.setTopic(consumerMetadata.getTopic());
        subscriptionData.setExpressionType(ExpressionType.SQL92);

        StringBuffer tagExpression = new StringBuffer();
        tagExpression.append(consumerMetadata.getSubString())
                .append(" and ")
                .append("(flowSphereTag is not null and flowSphereTag in (")
                .append(TagManager.getTag())
                .append("))");
        subscriptionData.setSubString(tagExpression.toString());
        return subscriptionData;
    }

    @Override
    public ExpressionTypeEnum getOriginExpressionType() {
        return ExpressionTypeEnum.SQL92;
    }
}
