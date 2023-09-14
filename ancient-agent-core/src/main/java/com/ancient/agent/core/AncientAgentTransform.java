package com.ancient.agent.core;

import com.ancient.agent.core.builder.InterceptorBuilderChain;
import com.ancient.agent.core.builder.MethodInterceptorBuilder;
import com.ancient.agent.core.builder.TargetObjectInterceptorBuilder;
import com.ancient.agent.core.classloader.AgentClassLoaderContext;
import com.ancient.agent.core.config.MethodMatcherConfigCreator;
import com.ancient.agent.core.config.yaml.YamlMethodPointcutConfig;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

public class AncientAgentTransform implements AgentBuilder.Transformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncientAgentTransform.class);


    private final Map<String, Collection<YamlMethodPointcutConfig>> classPointcutConfigMap;

    private final List<JarFile> jarFileList;

    public AncientAgentTransform(Map<String, Collection<YamlMethodPointcutConfig>> classPointcutConfigMap, List<JarFile> jarFileList) {
        this.classPointcutConfigMap = classPointcutConfigMap;
        this.jarFileList = jarFileList;
    }


    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {

        LOGGER.info("[AncientAgentTransform] init interceptor typeName={}", typeDescription.getTypeName());
        AgentClassLoaderContext agentClassLoaderContext = new AgentClassLoaderContext();
//        AgentClassLoaderContext.initAgentClassLoaderContext(classLoader,jarFileList);
        Collection<YamlMethodPointcutConfig> methodPointcutConfigs = classPointcutConfigMap.get(typeDescription.getTypeName());
        return InterceptorBuilderChain.buildInterceptor(builder, new TargetObjectInterceptorBuilder(),
                new MethodInterceptorBuilder(MethodMatcherConfigCreator.create(methodPointcutConfigs), typeDescription, agentClassLoaderContext));
    }

}
