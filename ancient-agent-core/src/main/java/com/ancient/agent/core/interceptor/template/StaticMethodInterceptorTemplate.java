package com.ancient.agent.core.interceptor.template;

import com.ancient.agent.core.interceptor.MethodInterceptorOperator;
import com.ancient.agent.core.interceptor.type.StaticMethodInterceptor;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.List;
import java.util.Map;

public class StaticMethodInterceptorTemplate implements MethodInterceptorOperator {

    private final Map<String, List<StaticMethodInterceptor>> interceptorMap;

    public StaticMethodInterceptorTemplate(Map<String, List<StaticMethodInterceptor>> interceptorMap) {
        this.interceptorMap = interceptorMap;
    }

    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder, MethodDescription pointcut) {
        return builder.method(ElementMatchers.is(pointcut)).intercept(MethodDelegation.withDefaultConfiguration().to(this));
    }

}
