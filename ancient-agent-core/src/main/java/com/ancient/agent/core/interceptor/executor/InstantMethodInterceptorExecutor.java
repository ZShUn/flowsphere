package com.ancient.agent.core.interceptor.executor;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptorResult;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InstantMethodInterceptorExecutor {


    /**
     * @param obj          目标对象
     * @param allArguments 注入目标方法的全部参数
     * @param callable     调用目标方法
     * @param method       目标方法
     * @return
     * @throws Exception
     */
    @RuntimeType
    public Object intercept(@This Object obj,
                            @AllArguments Object[] allArguments,
                            @SuperCall Callable<?> callable,
                            @Origin Method method) throws Exception {
        CustomContextAccessor customContextAccessor = (CustomContextAccessor) obj;
        try {
            InstantMethodInterceptorResult instantMethodInterceptorResult = new InstantMethodInterceptorResult();
            if (!instantMethodInterceptorResult.isContinue()) {
                return instantMethodInterceptorResult.getResult();
            }
            Object result = callable.call();
            if (!instantMethodInterceptorResult.isContinue()) {
                return instantMethodInterceptorResult.getResult();
            }
            return result;
        } catch (Throwable e) {
            throw e;
        }

    }


}
