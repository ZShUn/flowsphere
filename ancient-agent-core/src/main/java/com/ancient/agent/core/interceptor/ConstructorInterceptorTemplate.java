package com.ancient.agent.core.interceptor;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class ConstructorInterceptorTemplate {

    @RuntimeType
    public static void intercept(
            @This Object obj,
            @AllArguments Object[] allArguments) {
        ConstructorInterceptorManager.getMethodInterceptorList()
                .forEach(constructorInterceptor -> constructorInterceptor.onConstructor(obj, allArguments));
    }

}
