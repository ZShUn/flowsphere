package com.ancient.plugin.rocketmq;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.rule.context.TagContext;
import org.apache.rocketmq.common.message.MessageExt;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

public class RocketMQSubmitConsumeRequestMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        List<MessageExt> msgList = (List<MessageExt>) allArguments[0];
        MessageExt messageExt = msgList.get(0);
        String tag = messageExt.getUserProperty(CommonConstant.TAG);
        TagContext.set(tag);
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
        TagContext.remove();
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}