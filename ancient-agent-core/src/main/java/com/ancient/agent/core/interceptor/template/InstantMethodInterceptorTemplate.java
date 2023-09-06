package com.ancient.agent.core.interceptor.template;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.MethodInterceptorOperator;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class InstantMethodInterceptorTemplate implements MethodInterceptorOperator {

    private final Map<String, List<InstantMethodInterceptor>> interceptorMap;

    public InstantMethodInterceptorTemplate(Map<String, List<InstantMethodInterceptor>> interceptorMap) {
        this.interceptorMap = interceptorMap;
    }

    /**
     * @param obj          目标对象
     * @param allArguments 注入目标方法的全部参数
     * @param callable     调用目标方法
     * @param method       目标方法
     * @return
     * @throws Exception
     */
    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> callable, @Origin Method method) throws Exception {
        CustomContextAccessor customContextAccessor = (CustomContextAccessor) obj;
        Object result = null;
        InstantMethodInterceptorResult instantMethodInterceptorResult = new InstantMethodInterceptorResult();
        try {
            for (Map.Entry<String, List<InstantMethodInterceptor>> entry : interceptorMap.entrySet()) {
                for (InstantMethodInterceptor interceptor : entry.getValue()) {
                    interceptor.beforeMethod(customContextAccessor, allArguments, callable, method, instantMethodInterceptorResult);
                }
            }
            if (!instantMethodInterceptorResult.isContinue()) {
                return instantMethodInterceptorResult.getResult();
            }
            result = callable.call();
            return result;
        } catch (Throwable e) {
            for (Map.Entry<String, List<InstantMethodInterceptor>> entry : interceptorMap.entrySet()) {
                for (InstantMethodInterceptor interceptor : entry.getValue()) {
                    interceptor.exceptionMethod(customContextAccessor, allArguments, callable, method, e);
                }
            }
            throw e;
        } finally {
            for (Map.Entry<String, List<InstantMethodInterceptor>> entry : interceptorMap.entrySet()) {
                for (InstantMethodInterceptor interceptor : entry.getValue()) {
                    interceptor.afterMethod(customContextAccessor, allArguments, callable, method, result);
                }
            }
        }

    }


    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder, MethodDescription pointcut) {
        return builder.method(ElementMatchers.is(pointcut)).intercept(MethodDelegation.withDefaultConfiguration().to(this));
    }

}
