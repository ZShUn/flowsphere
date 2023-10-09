package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.StaticMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.rule.TagManager;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQBuildSubscriptionDataMethodInterceptor implements StaticMethodInterceptor {

    @Override
    public void beforeMethod(Class<?> clazz, Method method, Object[] args, Callable<?> callable, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        String topic = (String) args[1];
        String subString = (String) args[2];
        SubscriptionData subscriptionData = new SubscriptionData();
        subscriptionData.setTopic(topic);
        subscriptionData.setExpressionType(ExpressionType.SQL92);

        if (subString.equals("*")) {
            //(tag is not null and tag = xxx)
            StringBuffer tagExpression = new StringBuffer();
            tagExpression.append("(tag is not null and tag='");
            tagExpression.append(TagManager.getTag());
            tagExpression.append("')");

            subscriptionData.setSubString(tagExpression.toString());
            instantMethodInterceptorResult.setContinue(false);
            instantMethodInterceptorResult.setResult(subscriptionData);
            return;
        }

        String[] tags = subString.split("\\|\\|");
        if (subscriptionData.getTopic().contains("%RETRY%")) {
            return;
        }
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
        tagExpression.append(" and ");
        tagExpression.append("(tag is not null and tag='");
        tagExpression.append(TagManager.getTag());
        tagExpression.append("')");
        subscriptionData.setSubString(tagExpression.toString());
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
