package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.StaticMethodInterceptor;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQBuildSubscriptionDataMethodInterceptor implements StaticMethodInterceptor {

    @Override
    public void beforeMethod(Class<?> clazz, Method method, Object[] args, Callable<?> callable, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        String topic = (String) args[0];
        String subString = (String) args[1];
        SubscriptionData subscriptionData = new SubscriptionData();
        subscriptionData.setTopic(topic);
        subscriptionData.setExpressionType(ExpressionType.SQL92);

        String[] tags = subString.split("\\|\\|");
        if (subscriptionData.getTopic().contains("%RETRY%")) {
            return;
        }
        StringBuffer tagExpression = new StringBuffer();
        tagExpression.append("(TAGS is not null and TAGS in ");
        if (tags.length == 1) {
            tagExpression.append("('").append(tags[0].trim()).append("')");
            subscriptionData.setSubString(tagExpression.toString());
        } else {
            for (int i = 0; i < tags.length; i++) {
                String tagStr = tags[i];
                if (i == 0) {
                    tagExpression.append("('").append(tagStr.trim()).append("'");
                } else if ((i + 1) == tags.length) {
                    tagExpression.append(",'").append(tagStr.trim()).append("'))");
                } else {
                    tagExpression.append(",'").append(tagStr.trim()).append("'");
                }
            }
        }
        instantMethodInterceptorResult.setContinue(false);
        instantMethodInterceptorResult.setResult(subscriptionData);
    }

    @Override
    public void afterMethod(Class<?> clazz, Method method, Object[] args, Object result) {

    }

    @Override
    public void exceptionMethod(Class<?> clazz, Method method, Object[] args, Throwable throwable) {

    }

}
