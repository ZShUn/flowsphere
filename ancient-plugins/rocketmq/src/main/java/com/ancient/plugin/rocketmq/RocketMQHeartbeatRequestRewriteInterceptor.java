package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.ConsumerData;
import org.apache.rocketmq.common.protocol.heartbeat.HeartbeatData;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.Callable;

public class RocketMQHeartbeatRequestRewriteInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        HeartbeatData heartbeatData = (HeartbeatData) allArguments[1];
        for (ConsumerData consumerData : heartbeatData.getConsumerDataSet()) {
            for (SubscriptionData subscriptionData : consumerData.getSubscriptionDataSet()) {
                if (!subscriptionData.getTopic().contains("%RETRY%")) {
                    if (subscriptionData.getExpressionType().equals(ExpressionType.TAG)) {
                        subscriptionData.setExpressionType(ExpressionType.SQL92);
                        Set<String> tagSet = subscriptionData.getTagsSet();
                        StringBuffer tagExpression = new StringBuffer();
                        tagExpression.append("(TAGS is not null and TAGS in ");
                        int i = 0;
                        for (String tag : tagSet) {
                            if (i == 0) {
                                tagExpression.append("('").append(tag.trim()).append("'");
                            } else if ((i + 1) == tagSet.size()) {
                                tagExpression.append(",'").append(tag.trim()).append("'))");
                            } else {
                                tagExpression.append(",'").append(tag.trim()).append("'");
                            }
                            i++;
                        }
                        subscriptionData.setSubString(tagExpression.toString());
                    }
                    if (subscriptionData.getExpressionType().equals(ExpressionType.SQL92)) {
                        StringBuffer tagExpression = new StringBuffer();
                        tagExpression.append(subscriptionData.getSubString())
                                .append(" and (").append("grayTag=").append("grayTag").append(")");
                        subscriptionData.setSubString(tagExpression.toString());
                    }
                }
            }
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }


}
