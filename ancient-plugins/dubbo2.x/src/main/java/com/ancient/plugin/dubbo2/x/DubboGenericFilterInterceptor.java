package com.ancient.plugin.dubbo2.x;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.rule.context.TagContext;
import com.google.common.base.Strings;
import org.apache.dubbo.rpc.RpcContext;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class DubboGenericFilterInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        String tag = TagContext.get();
        if (!Strings.isNullOrEmpty(tag)) {
            RpcContext.getContext().setAttachment(CommonConstant.TAG, tag);
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
