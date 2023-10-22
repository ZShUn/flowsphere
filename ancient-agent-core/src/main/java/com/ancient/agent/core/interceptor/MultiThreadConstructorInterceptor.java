package com.ancient.agent.core.interceptor;

import com.ancient.agent.core.context.CustomContext;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.common.rule.context.TagContext;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class MultiThreadConstructorInterceptor {

    @RuntimeType
    public void constructorMethodIntercept(@This Object obj, @AllArguments Object[] allArguments) {
        CustomContextAccessor customContextAccessor = (CustomContextAccessor) obj;
        customContextAccessor.setCustomContext(new CustomContext(TagContext.get()));
    }

}
