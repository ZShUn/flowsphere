package com.ancient.agent.core;

import com.ancient.agent.core.builder.InterceptorBuilderChain;
import com.ancient.agent.core.builder.MultiThreadMethodInterceptorBuilder;
import com.ancient.agent.core.builder.PluginsMethodInterceptorBuilder;
import com.ancient.agent.core.builder.TargetObjectInterceptorBuilder;
import com.ancient.agent.core.classloader.AgentPluginClassLoader;
import com.ancient.agent.core.config.MethodMatcherConfigCreator;
import com.ancient.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.ancient.agent.core.interceptor.MultiThreadConstructorInterceptor;
import com.ancient.agent.core.interceptor.MultiThreadMethodInterceptor;
import com.ancient.agent.core.matcher.MultiThreadMethodMatch;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class AncientAgentTransform implements AgentBuilder.Transformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncientAgentTransform.class);

    private final Map<String, Collection<YamlMethodPointcutConfig>> classPointcutConfigMap;

    private final AgentPluginClassLoader agentPluginClassLoader;

    public AncientAgentTransform(Map<String, Collection<YamlMethodPointcutConfig>> classPointcutConfigMap, AgentPluginClassLoader agentPluginClassLoader) {
        this.classPointcutConfigMap = classPointcutConfigMap;
        this.agentPluginClassLoader = agentPluginClassLoader;
    }

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
        try {
            LOGGER.info("[AncientAgentTransform] load interceptor typeName={}", typeDescription.getTypeName());
            Collection<YamlMethodPointcutConfig> methodPointcutConfigs = classPointcutConfigMap.get(typeDescription.getTypeName());
            return InterceptorBuilderChain.buildInterceptor(builder, new TargetObjectInterceptorBuilder(),
                    new PluginsMethodInterceptorBuilder(MethodMatcherConfigCreator.create(methodPointcutConfigs), typeDescription, agentPluginClassLoader),
                    new MultiThreadMethodInterceptorBuilder()
            );
        } catch (Throwable e) {
            LOGGER.error("AncientAgentTransform load error", e);
            return builder;
        }
    }

}
