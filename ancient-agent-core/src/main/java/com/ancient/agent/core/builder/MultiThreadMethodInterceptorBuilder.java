package com.ancient.agent.core.builder;

import com.ancient.agent.core.interceptor.MultiThreadMethodInterceptor;
import com.ancient.agent.core.matcher.MultiThreadMethodMatch;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

public class MultiThreadMethodInterceptorBuilder implements InterceptorBuilder {

    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder) {
        builder.method(new MultiThreadMethodMatch()).intercept(MethodDelegation.to(MultiThreadMethodInterceptor.class));
        return builder;
    }

}
