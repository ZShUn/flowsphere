package com.ancient.plugin.feign;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.InstantMethodInterceptor;
import com.ancient.agent.core.interceptor.InstantInterceptorResult;
import com.ancient.plugin.feign.enhance.FeignRequestEnhance;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class FeignInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantInterceptorResult instantInterceptorResult) {
//        if (FeignInterceptorManager.isInterceptorMethod(method.getName())) {
            FeignRequestEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, instantInterceptorResult);
//        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantInterceptorResult instantInterceptorResult) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
