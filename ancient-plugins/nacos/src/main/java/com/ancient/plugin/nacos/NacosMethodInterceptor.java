package com.ancient.plugin.nacos;

import com.ancient.plugin.nacos.enhance.CustomContextEnhance;
import com.ancient.plugin.nacos.enhance.FeignRequestEnhance;
import com.ancient.plugin.nacos.enhance.NacosServerEnhance;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.MethodInterceptor;
import com.ancient.agent.core.interceptor.MethodInterceptorResult;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class NacosMethodInterceptor implements MethodInterceptor {

    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, MethodInterceptorResult methodInterceptorResult) {
        if (NacosInterceptorManager.isInterceptorMethod(method.getName())) {
            CustomContextEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, methodInterceptorResult);
            FeignRequestEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, null, methodInterceptorResult);
        }
    }

    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, MethodInterceptorResult methodInterceptorResult) {
        if (NacosInterceptorManager.isInterceptorMethod(method.getName())) {
            NacosServerEnhance.getInstance().enhance(customContextAccessor, allArguments, callable, method, result, methodInterceptorResult);
        }
    }

    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
