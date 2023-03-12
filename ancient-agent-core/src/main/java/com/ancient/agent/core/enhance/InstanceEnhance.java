package com.ancient.agent.core.enhance;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.MethodInterceptorResult;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public interface InstanceEnhance {

    void enhance(CustomContextAccessor customContextAccessor,
                 Object[] allArguments,
                 Callable<?> callable,
                 Method method,
                 Object result,
                 MethodInterceptorResult methodInterceptorResult);

}
