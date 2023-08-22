package com.ancient.agent.core;

import com.ancient.agent.core.utils.AgentPath;
import com.ancient.agent.core.utils.FileUtils;
import com.ancient.agent.core.utils.URLClassLoaderFactory;
import com.ancient.common.cache.JsonPath;
import com.ancient.common.cache.RuleCache;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.entity.RuleEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AncientBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncientBootstrap.class);

    public static void load() {

        ruleLoad();

        serviceLoad();

    }

    private static void ruleLoad() {
        try {
            File file = JsonPath.getPath();
            if (Objects.isNull(file)) {
                LOGGER.warn("rule.json file is null");
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            RuleEntity ruleEntity = objectMapper.readValue(file, RuleEntity.class);
            RuleCache.put(CommonConstant.RULE_KEY, ruleEntity);
        } catch (Exception e) {
            LOGGER.error("load rule config error", e);
        }
    }

    private static void serviceLoad() {

//        InputStream inputStream = classLoader.getResourceAsStream(String.join(File.separator, "nacos-agent.yaml"));
//        InputStream inputStream1 = classLoader.getResourceAsStream(String.join(File.separator, "rocketmq-agent.yaml"));
//        CustomYamlParse customYamlParse = new CustomYamlParse();
//        YamlPointcutConfig yamlPointcutConfig = customYamlParse.parse(inputStream);
//        YamlPointcutConfig yamlPointcutConfig1 = customYamlParse.parse(inputStream1);
//        ServiceLoader<ClassMatcher> classMatcherServiceLoader = ServiceLoader.load(ClassMatcher.class, classLoader);
//
//        for (ClassMatcher classMatcher : classMatcherServiceLoader) {
//            ClassMatcherManager.register(classMatcher);
//        }
//
//        ServiceLoader<MethodMatcher> methodMatcherServiceLoader = ServiceLoader.load(MethodMatcher.class, classLoader);
//        for (MethodMatcher methodMatcher : methodMatcherServiceLoader) {
//            MethodMatcherManager.register(methodMatcher);
//        }
//
//        ServiceLoader<InstantMethodInterceptor> methodInterceptorServiceLoader = ServiceLoader.load(InstantMethodInterceptor.class, classLoader);
//        for (InstantMethodInterceptor instantMethodInterceptor : methodInterceptorServiceLoader) {
//            InstantMethodInterceptorManager.register(instantMethodInterceptor);
//        }
//
//        ServiceLoader<ConstructorInterceptor> constructorInterceptorServiceLoader = ServiceLoader.load(ConstructorInterceptor.class, classLoader);
//        for (ConstructorInterceptor constructorInterceptor : constructorInterceptorServiceLoader) {
//            ConstructorInterceptorManager.register(constructorInterceptor);
//        }
    }




}
