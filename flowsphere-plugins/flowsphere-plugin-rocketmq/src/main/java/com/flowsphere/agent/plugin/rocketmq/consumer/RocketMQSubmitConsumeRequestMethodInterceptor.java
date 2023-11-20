package com.flowsphere.agent.plugin.rocketmq.consumer;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.rule.context.TagContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class RocketMQSubmitConsumeRequestMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        List<MessageExt> msgList = (List<MessageExt>) allArguments[0];
        MessageExt messageExt = msgList.get(0);
        String tag = messageExt.getUserProperty(CommonConstant.TAG);
        if (log.isDebugEnabled()) {
            log.debug("consumer get messageExt tag={}", tag);
        }
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
