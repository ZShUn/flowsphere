package com.ancient.plugin.async.rule.context;

import com.ancient.agent.core.context.CustomContext;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.MethodInterceptor;
import com.ancient.agent.core.interceptor.MethodInterceptorResult;
import com.ancient.common.context.RuleContext;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

public class CallableMethodInterceptor implements MethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, MethodInterceptorResult methodInterceptorResult) {
        if (AsyncContextInterceptorManager.isInterceptorMethod(method.getName())) {
            Object object = customContextAccessor.getCustomContext();
            if (Objects.nonNull(object) && object instanceof CustomContext) {
                CustomContext customContext = (CustomContext) object;
                RuleContext.set(customContext.getRuleContextStr());
            }
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, MethodInterceptorResult methodInterceptorResult) {
        if (AsyncContextInterceptorManager.isInterceptorMethod(method.getName())) {
            RuleContext.remove();
        }
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
