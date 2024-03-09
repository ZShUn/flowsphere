package com.flowsphere.agent.core;

import com.flowsphere.agent.core.builder.InterceptorBuilderChain;
import com.flowsphere.agent.core.builder.MultiThreadMethodInterceptorBuilder;
import com.flowsphere.agent.core.builder.PluginsMethodInterceptorBuilder;
import com.flowsphere.agent.core.builder.TargetObjectInterceptorBuilder;
import com.flowsphere.agent.core.classloader.AgentPluginClassLoader;
import com.flowsphere.agent.core.config.MethodMatcherConfigCreator;
import com.flowsphere.agent.core.config.yaml.YamlMethodPointcutConfig;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class AgentTransform implements AgentBuilder.Transformer {

    private final Map<String, Collection<YamlMethodPointcutConfig>> classPointcutConfigMap;

    private final AgentPluginClassLoader agentPluginClassLoader;

    public AgentTransform(Map<String, Collection<YamlMethodPointcutConfig>> classPointcutConfigMap, AgentPluginClassLoader agentPluginClassLoader) {
        this.classPointcutConfigMap = classPointcutConfigMap;
        this.agentPluginClassLoader = agentPluginClassLoader;
    }

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, ProtectionDomain protectionDomain) {
        try {
            log.info("[FlowSphereAgentTransform] load interceptor typeName={}", typeDescription.getTypeName());
            Collection<YamlMethodPointcutConfig> methodPointcutConfigs = classPointcutConfigMap.get(typeDescription.getTypeName());
            return InterceptorBuilderChain.buildInterceptor(builder, new TargetObjectInterceptorBuilder(), new PluginsMethodInterceptorBuilder(MethodMatcherConfigCreator.create(methodPointcutConfigs), typeDescription, agentPluginClassLoader), new MultiThreadMethodInterceptorBuilder());
        } catch (Throwable e) {
            log.error("[FlowSphereAgentTransform] load error", e);
            return builder;
        }
    }
}
