package com.ancient.agent.core.interceptor.executor;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class ConstructorInterceptorExecutor {

    @RuntimeType
    public void intercept(
            @This Object obj,
            @AllArguments Object[] allArguments) {
//        ConstructorInterceptorManager.getMethodInterceptorList()
//                .forEach(constructorInterceptor -> constructorInterceptor.onConstructor(obj, allArguments));
    }

}
