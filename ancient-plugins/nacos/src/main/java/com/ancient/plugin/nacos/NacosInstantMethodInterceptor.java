package com.ancient.plugin.nacos;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.plugin.nacos.enhance.NacosServerEnhance;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class NacosInstantMethodInterceptor implements InstantMethodInterceptor {

    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {

    }

    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantMethodInterceptorResult instantMethodInterceptorResult) {
//        if (NacosInterceptorManager.isInterceptorMethod(method.getName())) {
            NacosServerEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, result, instantMethodInterceptorResult);
//        }
    }

    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
