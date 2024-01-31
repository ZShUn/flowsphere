package com.flowsphere.agent.plugin.fegin.enhance;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.enhance.InstanceEnhance;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.common.constant.CommonConstant;
import com.flowsphere.common.rule.context.TagContext;
import com.google.common.base.Strings;
import feign.Request;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;

@Slf4j
public class FeignRequestEnhance implements InstanceEnhance {

    private static final FeignRequestEnhance INSTANCE = new FeignRequestEnhance();

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignRequestEnhance.class);

    private static final String METHOD_NAME = "execute";

    private static final String DECLARED_FIELD_NAME = "headers";


    public static FeignRequestEnhance getInstance() {
        return INSTANCE;
    }

    @Override
    public void enhance(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, Object result, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        if (method.getName().equals(METHOD_NAME)) {
            if (allArguments.length > 0 && allArguments[0] instanceof Request) {
                Request request = (Request) allArguments[0];
                Map<String, Collection<String>> headers = new LinkedHashMap<String, Collection<String>>();
                headers.putAll(request.headers());
                resolver(headers);
                try {
                    Field headersField = Request.class.getDeclaredField(DECLARED_FIELD_NAME);
                    headersField.setAccessible(true);
                    headersField.set(request, Collections.unmodifiableMap(headers));
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
        }
    }


    private void resolver(Map<String, Collection<String>> headers) {
        String tag = TagContext.get();
        log.info("feign tag={}", tag);
        if (!Strings.isNullOrEmpty(tag)) {
            List<String> ruleList = new ArrayList<String>();
            ruleList.add(tag);
            headers.put(CommonConstant.TAG, ruleList);
        }
    }

}
