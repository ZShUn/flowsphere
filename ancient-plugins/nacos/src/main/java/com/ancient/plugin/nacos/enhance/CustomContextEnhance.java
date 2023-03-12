package com.ancient.plugin.nacos.enhance;

import com.ancient.agent.core.context.CustomContext;
import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.enhance.InstanceEnhance;
import com.ancient.agent.core.interceptor.MethodInterceptorResult;
import com.ancient.common.constant.HeaderConstant;
import com.ancient.common.context.RuleContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

public class CustomContextEnhance implements InstanceEnhance {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomContextEnhance.class);

    private static final CustomContextEnhance INSTANCE = new CustomContextEnhance();

    public static CustomContextEnhance getInstance() {
        return INSTANCE;
    }

    @Override
    public void enhance(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, MethodInterceptorResult methodInterceptorResult) {
        CustomContext customContext = (CustomContext) customContextAccessor.getCustomContext();
        Optional.ofNullable(getRuleJson()).orElseGet(() -> {
            if (Objects.nonNull(customContext) && StringUtils.isNotBlank(customContext.getRuleContextStr())) {
                RuleContext.set(customContext.getRuleContextStr());
            }
            if (StringUtils.isNotBlank(RuleContext.get())) {
                customContextAccessor.setCustomContext(new CustomContext(RuleContext.get()));
            }
            return null;
        });
    }

    private String getRuleJson() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            return null;
        }
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        return httpServletRequest.getHeader(HeaderConstant.RULE);
    }

}
