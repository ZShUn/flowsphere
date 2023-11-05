package com.ancient.agent.core;

import com.ancient.agent.core.builder.InterceptorBuilderChain;
import com.ancient.agent.core.builder.MultiThreadMethodInterceptorBuilder;
import com.ancient.agent.core.builder.PluginsMethodInterceptorBuilder;
import com.ancient.agent.core.builder.TargetObjectInterceptorBuilder;
import com.ancient.agent.core.classloader.AgentPluginClassLoader;
import com.ancient.agent.core.config.MethodMatcherConfigCreator;
import com.ancient.agent.core.config.yaml.YamlMethodPointcutConfig;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

@Slf4j
public class AncientAgentTransform implements AgentBuilder.Transformer {

    private final Map<String, Collection<YamlMethodPointcutConfig>> classPointcutConfigMap;

    private final AgentPluginClassLoader agentPluginClassLoader;

    public AncientAgentTransform(Map<String, Collection<YamlMethodPointcutConfig>> classPointcutConfigMap, AgentPluginClassLoader agentPluginClassLoader) {
        this.classPointcutConfigMap = classPointcutConfigMap;
        this.agentPluginClassLoader = agentPluginClassLoader;
    }

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
        try {
            log.info("[AncientAgentTransform] load interceptor typeName={}", typeDescription.getTypeName());
            Collection<YamlMethodPointcutConfig> methodPointcutConfigs = classPointcutConfigMap.get(typeDescription.getTypeName());
            return InterceptorBuilderChain.buildInterceptor(builder, new TargetObjectInterceptorBuilder(),
                    new PluginsMethodInterceptorBuilder(MethodMatcherConfigCreator.create(methodPointcutConfigs), typeDescription, agentPluginClassLoader),
                    new MultiThreadMethodInterceptorBuilder()
            );
        } catch (Throwable e) {
            log.error("AncientAgentTransform load error", e);
            return builder;
        }
    }

}
