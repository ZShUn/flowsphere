package com.ancient.plugin.nacos;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.InstantMethodInterceptor;
import com.ancient.agent.core.interceptor.InstantInterceptorResult;
import com.ancient.plugin.nacos.enhance.NacosServerEnhance;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class NacosInstantMethodInterceptor implements InstantMethodInterceptor {

    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantInterceptorResult instantInterceptorResult) {

    }

    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantInterceptorResult instantInterceptorResult) {
        if (NacosInterceptorManager.isInterceptorMethod(method.getName())) {
            NacosServerEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, result, instantInterceptorResult);
        }
    }

    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
