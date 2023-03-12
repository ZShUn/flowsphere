package com.ancient.agent.core.interceptor;

import com.ancient.agent.core.context.CustomContextAccessor;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MethodInterceptorTemplate {


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
            MethodInterceptorResult methodInterceptorResult = new MethodInterceptorResult();
            for (MethodInterceptor methodInterceptor : MethodInterceptorManager.getMethodInterceptorList()) {
                methodInterceptor.beforeMethod(customContextAccessor, allArguments, callable, method, methodInterceptorResult);
            }
            if (!methodInterceptorResult.isContinue()) {
                return methodInterceptorResult.getResult();
            }
            Object result = callable.call();
            for (MethodInterceptor methodInterceptor : MethodInterceptorManager.getMethodInterceptorList()) {
                methodInterceptor.afterMethod(customContextAccessor, allArguments, callable, method, result, methodInterceptorResult);
            }
            if (!methodInterceptorResult.isContinue()) {
                return methodInterceptorResult.getResult();
            }
            return result;
        } catch (Throwable e) {
            MethodInterceptorManager.getMethodInterceptorList()
                    .forEach(interceptor -> {
                        interceptor.exceptionMethod(customContextAccessor, allArguments, callable, method, e);
                    });
            throw e;
        }

    }


}
