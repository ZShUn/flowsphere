package com.ancient.agent.core.interceptor.template;

import com.ancient.agent.core.interceptor.MethodInterceptorOperator;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.StaticMethodInterceptor;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class StaticMethodInterceptorTemplate implements MethodInterceptorOperator {

    private final Map<String, List<StaticMethodInterceptor>> interceptorMap;

    public StaticMethodInterceptorTemplate(Map<String, List<StaticMethodInterceptor>> interceptorMap) {
        this.interceptorMap = interceptorMap;
    }

    @RuntimeType
    public static Object intercept(@Origin final Class<?> klass, @This Object obj, @AllArguments Object[] allArguments, @SuperCall Callable<?> callable, @Origin Method method) throws Exception {
        //类似拦截器效果
        Object result = null;
        InstantMethodInterceptorResult instantMethodInterceptorResult = new InstantMethodInterceptorResult();
        try {
            for (Map.Entry<String, List<StaticMethodInterceptor>> entry : interceptorMap.entrySet()) {
                for (StaticMethodInterceptor interceptor : entry.getValue()) {
                    interceptor.beforeMethod(klass, method, allArguments, instantMethodInterceptorResult);
                }
            }
            if (!instantMethodInterceptorResult.isContinue()) {
                return instantMethodInterceptorResult.getResult();
            }
            result = callable.call();
            return result;
        } catch (Throwable e) {
            for (Map.Entry<String, List<StaticMethodInterceptor>> entry : interceptorMap.entrySet()) {
                for (StaticMethodInterceptor interceptor : entry.getValue()) {
                    interceptor.exceptionMethod(klass, method, allArguments, e);
                }
            }
            throw e;
        } finally {
            for (Map.Entry<String, List<StaticMethodInterceptor>> entry : interceptorMap.entrySet()) {
                for (StaticMethodInterceptor interceptor : entry.getValue()) {
                    interceptor.afterMethod(klass, method, allArguments, result);
                }
            }
        }

    }


    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder, MethodDescription pointcut) {
        return builder.method(ElementMatchers.is(pointcut)).intercept(MethodDelegation.withDefaultConfiguration().to(this));
    }

}
