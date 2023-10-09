package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.StaticMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.rule.TagManager;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQBuildMethodInterceptor implements StaticMethodInterceptor {

    @Override
    public void beforeMethod(Class<?> clazz, Method method, Object[] args, Callable<?> callable, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        String topic = (String) args[0];
        String subString = (String) args[1];
        String expressionType = (String) args[2];
        if (ExpressionType.SQL92.equals(expressionType)){
            SubscriptionData subscriptionData = new SubscriptionData();
            subscriptionData.setTopic(topic);
            subscriptionData.setExpressionType(ExpressionType.SQL92);

            if (subscriptionData.getTopic().contains("%RETRY%")) {
                return;
            }
            StringBuffer tagExpression = new StringBuffer();
            tagExpression.append(subString)
                    .append(" and (")
                    .append(CommonConstant.TAG)
                    .append("=")
                    .append(TagManager.getTag())
                    .append(")");
            subscriptionData.setSubString(tagExpression.toString());
            instantMethodInterceptorResult.setContinue(false);
            instantMethodInterceptorResult.setResult(subscriptionData);
        }

    }

    @Override
    public void afterMethod(Class<?> clazz, Method method, Object[] args, Object result) {

    }

    @Override
    public void exceptionMethod(Class<?> clazz, Method method, Object[] args, Throwable throwable) {

    }

}
