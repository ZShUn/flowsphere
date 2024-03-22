package com.flowsphere.agent.plugin.rocketmq.consumer.sql.expression;

import com.flowsphere.common.tag.TagManager;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

public class SimpleTagEnhance implements SQL92Enhance {

    @Override
    public SubscriptionData enhance(ConsumerMetadata consumerMetadata) {
        SubscriptionData subscriptionData = new SubscriptionData();
        subscriptionData.setTopic(consumerMetadata.getTopic());
        subscriptionData.setExpressionType(ExpressionType.SQL92);
        String[] tags = consumerMetadata.getSubString().split("\\|\\|");

        StringBuffer tagExpression = new StringBuffer();
        tagExpression.append("(TAGS is not null and TAGS in ");
        for (int i = 0; i < tags.length; i++) {
            String tagStr = tags[i];
            if (tags.length == 1) {
                tagExpression.append("('").append(tags[0].trim()).append("'))");
                subscriptionData.setSubString(tagExpression.toString());
            } else if (i == 0) {
                tagExpression.append("('").append(tagStr.trim()).append("'");
            } else if ((i + 1) == tags.length) {
                tagExpression.append(",'").append(tagStr.trim()).append("'))");
            } else {
                tagExpression.append(",'").append(tagStr.trim()).append("'");
            }
        }
        tagExpression.append(" and ")
                .append("(flowSphereTag is not null and flowSphereTag in (")
                .append(TagManager.getTag())
                .append("))");
        subscriptionData.setSubString(tagExpression.toString());
        return subscriptionData;
    }

    @Override
    public ExpressionTypeEnum getOriginExpressionType() {
        return ExpressionTypeEnum.TAG;
    }

}
