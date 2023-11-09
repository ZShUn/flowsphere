package com.flowsphere.agent.core.enhance;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public interface InstanceEnhance {

    void enhance(CustomContextAccessor customContextAccessor,
                 Object[] allArguments,
                 Callable<?> callable,
                 Method method,
                 Object result,
                 InstantMethodInterceptorResult instantMethodInterceptorResult);

}
