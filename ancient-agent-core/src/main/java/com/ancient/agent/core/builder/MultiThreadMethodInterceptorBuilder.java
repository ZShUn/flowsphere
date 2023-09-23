package com.ancient.agent.core.builder;

import com.ancient.agent.core.interceptor.MultiThreadConstructorInterceptor;
import com.ancient.agent.core.interceptor.MultiThreadMethodInterceptor;
import com.ancient.agent.core.matcher.MultiThreadMethodMatch;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

public class MultiThreadMethodInterceptorBuilder implements InterceptorBuilder {

    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder) {
        builder.method(new MultiThreadMethodMatch())
                .intercept(MethodDelegation.withDefaultConfiguration().to(new MultiThreadMethodInterceptor()))
                .constructor(ElementMatchers.isConstructor())
                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration().to(new MultiThreadConstructorInterceptor())));
        return builder;
    }

}
