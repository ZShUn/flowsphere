package com.ancient.agent.core.interceptor;

import com.ancient.agent.core.context.CustomContext;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.common.context.RuleContext;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

public class MultiThreadMethodInterceptor {

    @RuntimeType
    public Object instantMethodIntercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> callable, @Origin Method method) throws Exception {
        try {
            CustomContextAccessor customContextAccessor = (CustomContextAccessor) obj;
            Object object = customContextAccessor.getCustomContext();
            if (Objects.nonNull(object) && object instanceof CustomContext) {
                CustomContext customContext = (CustomContext) object;
                RuleContext.set(customContext.getRuleContextStr());
            }
            Object result = callable.call();
            return result;
        } finally {
            RuleContext.remove();
        }
    }

}
