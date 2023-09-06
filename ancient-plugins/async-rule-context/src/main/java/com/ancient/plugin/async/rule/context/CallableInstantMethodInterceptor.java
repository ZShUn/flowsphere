package com.ancient.plugin.async.rule.context;

import com.ancient.agent.core.context.CustomContext;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.common.context.RuleContext;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

public class CallableInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        Object object = customContextAccessor.getCustomContext();
        if (Objects.nonNull(object) && object instanceof CustomContext) {
            CustomContext customContext = (CustomContext) object;
            RuleContext.set(customContext.getRuleContextStr());
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        RuleContext.remove();
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
