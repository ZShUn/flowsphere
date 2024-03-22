package com.flowsphere.agent.plugin.dubbo2;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.tag.context.TagContext;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@Slf4j
public class DubboGenericFilterInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
//        String tag = TagContext.get();
//        if (!Strings.isNullOrEmpty(tag)) {
//            RpcContext.getContext().setAttachment(CommonConstant.TAG, tag);
//        }
        RpcContext rpcContext = RpcContext.getContext();
        String tag = rpcContext.getAttachment(CommonConstant.TAG);
        if (!Strings.isNullOrEmpty(tag)) {
            if (log.isDebugEnabled()) {
                log.debug("[FlowSphere] DubboGenericFilterInterceptor dubbo tag={}", tag);
            }
            TagContext.set(tag);
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
        TagContext.remove();
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
