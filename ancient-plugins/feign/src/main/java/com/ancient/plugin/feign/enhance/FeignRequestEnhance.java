package com.ancient.plugin.feign.enhance;

import com.ancient.agent.core.context.CustomContextAccessor;
import com.ancient.agent.core.enhance.InstanceEnhance;
import com.ancient.agent.core.interceptor.type.InstantMethodInterceptorResult;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.context.RuleContext;
import com.ancient.common.entity.RuleEntity;
import feign.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;

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
        RuleEntity ruleEntity = RuleContext.get();
        if (Objects.nonNull(ruleEntity.getVersionEntity())) {
            List<String> versionList = new ArrayList<String>();
            versionList.add(ruleEntity.getVersionEntity().getVersion());
            headers.put(CommonConstant.VERSION, versionList);
        }
        if (Objects.nonNull(ruleEntity.getRegionEntity())) {
            List<String> regionList = new ArrayList<String>();
            regionList.add(ruleEntity.getRegionEntity().getRegion());
            headers.put(CommonConstant.REGION, regionList);
        }
    }

}
