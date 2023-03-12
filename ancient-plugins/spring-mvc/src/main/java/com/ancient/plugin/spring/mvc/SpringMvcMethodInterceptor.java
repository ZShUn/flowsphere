package com.ancient.plugin.spring.mvc;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.MethodInterceptor;
import com.ancient.agent.core.interceptor.MethodInterceptorResult;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.context.RuleContext;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SpringMvcMethodInterceptor implements MethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, MethodInterceptorResult methodInterceptorResult) {
        if (SpringMvcInterceptorManager.isInterceptorMethod(method.getName())) {
            if (allArguments.length > 0 && allArguments[0] instanceof ServletRequest) {
                ServletRequest request = (ServletRequest) allArguments[0];
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                RuleContext.set(httpRequest.getHeader(CommonConstant.VERSION));
            }
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, MethodInterceptorResult methodInterceptorResult) {

    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
