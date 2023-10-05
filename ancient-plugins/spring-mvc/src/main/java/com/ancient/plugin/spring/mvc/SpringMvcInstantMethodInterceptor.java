package com.ancient.plugin.spring.mvc;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptor;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.context.RuleContext;
import com.ancient.common.entity.RuleEntity;
import com.ancient.common.util.JacksonUtils;
import com.google.common.base.Strings;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SpringMvcInstantMethodInterceptor implements InstantMethodInterceptor {

    @Override
    public void beforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (allArguments.length > 0 && allArguments[0] instanceof ServletRequest) {
            ServletRequest request = (ServletRequest) allArguments[0];
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            resolver(httpRequest);
        }
    }


    private void resolver(HttpServletRequest httpRequest) {
        String grayRule = httpRequest.getHeader(CommonConstant.GRAY_RULE);
        if (!Strings.isNullOrEmpty(grayRule)) {
            RuleEntity ruleEntity = JacksonUtils.toObj(grayRule, RuleEntity.class);
            RuleContext.set(ruleEntity);
        }
    }

    @Override
    public void afterMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result) {
        RuleContext.remove();
    }

    @Override
    public void exceptionMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Throwable e) {

    }

}
