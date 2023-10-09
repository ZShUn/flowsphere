package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.rule.TagManager;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class RocketMQSubscribeMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (allArguments[1] instanceof SubscriptionData) {
            SubscriptionData subscriptionData = (SubscriptionData) allArguments[1];
            if (subscriptionData.getTopic().equals("%RETRY%")) {
                return;
            }
            if (subscriptionData.getExpressionType().equals(ExpressionType.SQL92)) {
                StringBuffer tagExpression = new StringBuffer();
                tagExpression.append(subscriptionData.getSubString())
                        .append(" and (")
                        .append(CommonConstant.TAG)
                        .append("=")
                        .append(TagManager.getTag())
                        .append(")");
                subscriptionData.setSubString(tagExpression.toString());
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
