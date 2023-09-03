package com.ancient.agent.core.interceptor.type;

import com.ancient.agent.core.interceptor.MethodInterceptor;

public interface ConstructorInterceptor extends MethodInterceptor {

    void onConstructor(Object obj, Object[] allArguments);

}
