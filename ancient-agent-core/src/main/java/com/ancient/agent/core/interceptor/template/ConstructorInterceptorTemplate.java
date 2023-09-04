package com.ancient.agent.core.interceptor.template;

import com.ancient.agent.core.interceptor.MethodInterceptorOperator;
import com.ancient.agent.core.interceptor.type.StaticMethodInterceptor;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.List;
import java.util.Map;

public class ConstructorInterceptorTemplate implements MethodInterceptorOperator {

    private final Map<String, List<StaticMethodInterceptor>> interceptorMap;

    public ConstructorInterceptorTemplate(Map<String, List<StaticMethodInterceptor>> interceptorMap) {
        this.interceptorMap = interceptorMap;
    }

    @RuntimeType
    public void intercept(
            @This Object obj,
            @AllArguments Object[] allArguments) {
//        ConstructorInterceptorManager.getMethodInterceptorList()
//                .forEach(constructorInterceptor -> constructorInterceptor.onConstructor(obj, allArguments));
    }

    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder, MethodDescription pointcut) {
        return builder.constructor(ElementMatchers.isConstructor())
                .intercept(SuperMethodCall.INSTANCE.andThen(MethodDelegation.withDefaultConfiguration()
                        .to(this)));
    }

}
