package com.ancient.agent.core.interceptor;

import com.ancient.agent.core.context.CustomContextAccessor;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InstantInterceptorTemplate {


    /**
     * @param obj          目标对象
     * @param allArguments 注入目标方法的全部参数
     * @param callable     调用目标方法
     * @param method       目标方法
     * @return
     * @throws Exception
     */
    @RuntimeType
    public static Object intercept(@This Object obj,
                                   @AllArguments Object[] allArguments,
                                   @SuperCall Callable<?> callable,
                                   @Origin Method method) throws Exception {
        CustomContextAccessor customContextAccessor = (CustomContextAccessor) obj;
        try {
            InstantInterceptorResult instantInterceptorResult = new InstantInterceptorResult();
            for (InstantMethodInterceptor instantMethodInterceptor : InstantMethodInterceptorManager.getMethodInterceptorList()) {
                instantMethodInterceptor.beforeMethod(customContextAccessor, allArguments, callable, method, instantInterceptorResult);
            }
            if (!instantInterceptorResult.isContinue()) {
                return instantInterceptorResult.getResult();
            }
            Object result = callable.call();
            for (InstantMethodInterceptor instantMethodInterceptor : InstantMethodInterceptorManager.getMethodInterceptorList()) {
                instantMethodInterceptor.afterMethod(customContextAccessor, allArguments, callable, method, result, instantInterceptorResult);
            }
            if (!instantInterceptorResult.isContinue()) {
                return instantInterceptorResult.getResult();
            }
            return result;
        } catch (Throwable e) {
            InstantMethodInterceptorManager.getMethodInterceptorList()
                    .forEach(interceptor -> {
                        interceptor.exceptionMethod(customContextAccessor, allArguments, callable, method, e);
                    });
            throw e;
        }

    }


}
