package com.ancient.agent.core.interceptor.type;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public interface InstantMethodInterceptor extends MethodInterceptor {


    /**
     * @param customContextAccessor    目标对象
     * @param allArguments             注入目标方法的全部参数
     * @param callable                 调用目标方法
     * @param method                   目标方法
     * @param instantMethodInterceptorResult 自定义拦截器返回结果
     */
    void beforeMethod(CustomContextAccessor customContextAccessor,
                      Object[] allArguments,
                      Callable<?> callable,
                      Method method,
                      InstantMethodInterceptorResult instantMethodInterceptorResult);

    /**
     * @param customContextAccessor    目标对象
     * @param allArguments             注入目标方法的全部参数
     * @param callable                 调用目标方法
     * @param method                   目标方法
     * @param result                   方法执行结果
     * @param instantMethodInterceptorResult 自定义拦截器返回结果
     */
    void afterMethod(CustomContextAccessor customContextAccessor,
                     Object[] allArguments,
                     Callable<?> callable,
                     Method method,
                     Object result);

    /**
     * @param customContextAccessor 目标对象
     * @param allArguments          注入目标方法的全部参数
     * @param callable              调用目标方法
     * @param method                目标方法
     * @param e                     异常堆栈
     */
    void exceptionMethod(CustomContextAccessor customContextAccessor,
                         Object[] allArguments,
                         Callable<?> callable,
                         Method method,
                         Throwable e);


}
