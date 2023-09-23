package com.ancient.agent.core.interceptor;

import com.ancient.agent.core.context.CustomContext;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.common.context.RuleContext;
import net.bytebuddy.implementation.bind.annotation.*;

public class MultiThreadConstructorInterceptor {

    @RuntimeType
    public void constructorMethodIntercept(@This Object obj, @AllArguments Object[] allArguments) {
        CustomContextAccessor customContextAccessor = (CustomContextAccessor) obj;
        customContextAccessor.setCustomContext(new CustomContext(RuleContext.get()));
    }

}
