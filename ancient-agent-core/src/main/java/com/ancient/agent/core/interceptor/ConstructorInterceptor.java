package com.ancient.agent.core.interceptor;

public interface ConstructorInterceptor extends MethodInterceptor {

    void onConstructor(Object obj, Object[] allArguments);

}
