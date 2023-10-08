package com.ancient.agent.core.builder;

import com.ancient.agent.core.AncientBootstrap;
import com.ancient.agent.core.classloader.AgentPluginClassLoader;
import com.ancient.agent.core.config.MethodMatcherConfig;
import com.ancient.agent.core.interceptor.MethodInterceptor;
import com.ancient.agent.core.interceptor.MethodInterceptorManager;
import com.ancient.agent.core.interceptor.template.ConstructorInterceptorTemplate;
import com.ancient.agent.core.interceptor.template.InstantMethodInterceptorTemplate;
import com.ancient.agent.core.interceptor.template.StaticMethodInterceptorTemplate;
import com.ancient.agent.core.utils.MethodTypeUtils;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PluginsMethodInterceptorBuilder implements InterceptorBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncientBootstrap.class);

    private final List<MethodMatcherConfig> methodMatcherConfigs;

    private final TypeDescription typePointcut;

    private final AgentPluginClassLoader agentPluginClassLoader;

    public PluginsMethodInterceptorBuilder(List<MethodMatcherConfig> methodMatcherConfigs, TypeDescription typePointcut, AgentPluginClassLoader agentPluginClassLoader) {
        this.methodMatcherConfigs = methodMatcherConfigs;
        this.typePointcut = typePointcut;
        this.agentPluginClassLoader = agentPluginClassLoader;
    }

    @Override
    public DynamicType.Builder<?> intercept(DynamicType.Builder<?> builder) {

        try {
            for (MethodDescription.InDefinedShape each : typePointcut.getDeclaredMethods()) {
                Map<String, List<MethodInterceptor>> methodInterceptorMap = new HashMap<>();

                for (MethodMatcherConfig methodMatcherConfig : methodMatcherConfigs) {
                    if (methodMatcherConfig.getPointcut().matches(each)) {
                        methodInterceptorMap.computeIfAbsent(methodMatcherConfig.getType(), key -> new LinkedList<>());
                        methodInterceptorMap.get(methodMatcherConfig.getType()).add(MethodInterceptorManager.getMethodInterceptor(methodMatcherConfig.getInterceptorName(), agentPluginClassLoader));
                    }
                }

                if (methodInterceptorMap.isEmpty()) {
                    continue;
                }

                if (MethodTypeUtils.isInstantMethod(each)) {
                    builder = new InstantMethodInterceptorTemplate(convert(methodInterceptorMap)).intercept(builder, each);
                }
                if (MethodTypeUtils.isConstructorMethod(each)) {
                    builder = new ConstructorInterceptorTemplate(convert(methodInterceptorMap)).intercept(builder, each);
                }
                if (MethodTypeUtils.isStaticMethod(each)) {
                    builder = new StaticMethodInterceptorTemplate(convert(methodInterceptorMap)).intercept(builder, each);
                }

            }
        } catch (Throwable e) {
            LOGGER.error("[MethodInterceptorBuilder] init interceptor error", e);
        }
        return builder;
    }

    private <T extends MethodInterceptor> Map<String, List<T>> convert(final Map<String, List<MethodInterceptor>> advices) {
        Map<String, List<T>> result = new HashMap<>(advices.size(), 1F);
        for (Map.Entry<String, List<MethodInterceptor>> entry : advices.entrySet()) {
            result.put(entry.getKey(), new LinkedList<>());
            for (MethodInterceptor each : entry.getValue()) {
                result.get(entry.getKey()).add((T) each);
            }
        }
        return result;
    }


}
