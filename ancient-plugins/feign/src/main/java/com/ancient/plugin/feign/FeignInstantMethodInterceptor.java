package com.ancient.plugin.feign;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptorResult;
import com.ancient.plugin.feign.enhance.FeignRequestEnhance;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class FeignInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
//        if (FeignInterceptorManager.isInterceptorMethod(method.getName())) {
            FeignRequestEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, instantMethodInterceptorResult);
//        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantMethodInterceptorResult instantMethodInterceptorResult) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
