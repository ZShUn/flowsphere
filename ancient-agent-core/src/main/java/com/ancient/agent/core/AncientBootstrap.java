package com.ancient.agent.core;

import com.ancient.agent.core.interceptor.ConstructorInterceptor;
import com.ancient.agent.core.interceptor.ConstructorInterceptorManager;
import com.ancient.agent.core.interceptor.MethodInterceptor;
import com.ancient.agent.core.interceptor.MethodInterceptorManager;
import com.ancient.agent.core.matcher.ClassMatcher;
import com.ancient.agent.core.matcher.ClassMatcherManager;
import com.ancient.agent.core.matcher.MethodMatcher;
import com.ancient.agent.core.matcher.MethodMatcherManager;
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
                LOGGER.error("rule.json file is null");
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            RuleEntity ruleEntity = objectMapper.readValue(file, RuleEntity.class);
            RuleCache.put(CommonConstant.RULE_KEY, ruleEntity);
        } catch (IOException e) {
            LOGGER.error("load rule config error", e);
        }
    }

    private static void serviceLoad() {
        URL[] pluginUrls = getPlugin().toArray(new URL[]{});
        ClassLoader classLoader = URLClassLoaderFactory.createClassLoader(pluginUrls, AncientBootstrap.class.getClassLoader());
        ServiceLoader<ClassMatcher> classMatcherServiceLoader = ServiceLoader.load(ClassMatcher.class, classLoader);

        for (ClassMatcher classMatcher : classMatcherServiceLoader) {
            ClassMatcherManager.register(classMatcher);
        }

        ServiceLoader<MethodMatcher> methodMatcherServiceLoader = ServiceLoader.load(MethodMatcher.class, classLoader);
        for (MethodMatcher methodMatcher : methodMatcherServiceLoader) {
            MethodMatcherManager.register(methodMatcher);
        }

        ServiceLoader<MethodInterceptor> methodInterceptorServiceLoader = ServiceLoader.load(MethodInterceptor.class, classLoader);
        for (MethodInterceptor methodInterceptor : methodInterceptorServiceLoader) {
            MethodInterceptorManager.register(methodInterceptor);
        }

        ServiceLoader<ConstructorInterceptor> constructorInterceptorServiceLoader = ServiceLoader.load(ConstructorInterceptor.class, classLoader);
        for (ConstructorInterceptor constructorInterceptor : constructorInterceptorServiceLoader) {
            ConstructorInterceptorManager.register(constructorInterceptor);
        }
    }

    private static List<URL> getPlugin() {
        File agentDictionary = AgentPath.getPath();
        File plugins = new File(agentDictionary, "plugin");

        return resolveLib(plugins.getAbsolutePath());
    }

    private static List<URL> resolveLib(String agentLibPath) {
        final File libDir = new File(agentLibPath);
        if (checkDirectory(libDir)) {
            return Collections.emptyList();
        }
        final File[] libFileList = FileUtils.listFiles(libDir, new String[]{".jar"});

        List<URL> libURLList = toURLs(libFileList);
        URL agentDirUri = toURL(new File(agentLibPath));

        List<URL> jarURLList = new ArrayList<URL>(libURLList);
        jarURLList.add(agentDirUri);

        return jarURLList;
    }

    private static boolean checkDirectory(File file) {
        if (!file.exists()) {

            return true;
        }
        if (!file.isDirectory()) {
            return true;
        }

        return false;
    }

    private static List<URL> toURLs(File[] jarFileList) {
        try {
            URL[] jarURLArray = FileUtils.toURLs(jarFileList);

            return Arrays.asList(jarURLArray);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static URL toURL(File file) {
        try {
            return FileUtils.toURL(file);
        } catch (IOException e) {
            throw new IllegalArgumentException(file.getName() + ".toURL() failed.", e);
        }
    }


}
