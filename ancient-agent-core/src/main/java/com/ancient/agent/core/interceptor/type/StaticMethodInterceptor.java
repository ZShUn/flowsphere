package com.ancient.agent.core.interceptor.type;

import com.ancient.agent.core.interceptor.MethodInterceptor;

import java.lang.reflect.Method;

public interface StaticMethodInterceptor extends MethodInterceptor {

    void beforeMethod(Class<?> clazz, Method method, Object[] args, InstantMethodInterceptorResult instantMethodInterceptorResult);

    void afterMethod(Class<?> clazz, Method method, Object[] args, Object result);

    void exceptionMethod(Class<?> clazz, Method method, Object[] args, Throwable throwable);

}
