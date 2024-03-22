package com.flowsphere.agent;


import com.flowsphere.agent.core.AgentBootstrap;
import com.flowsphere.agent.core.AgentJunction;
import com.flowsphere.agent.core.AgentListener;
import com.flowsphere.agent.core.AgentTransform;
import com.flowsphere.agent.core.classloader.AgentClassLoaderManager;
import com.flowsphere.agent.core.classloader.AgentPluginClassLoader;
import com.flowsphere.agent.core.config.yaml.YamlAgentConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigLoaderManager;
import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
import com.flowsphere.agent.core.yaml.YamlResolver;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class FlowSphereAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("-------------------FlowSphereAgent开始启动-------------------");
        AgentPluginClassLoader agentPluginClassLoader = AgentClassLoaderManager.getAgentPluginClassLoader(FlowSphereAgent.class.getClassLoader());
        YamlAgentConfig yamlAgentConfig = YamlResolver.parseAgentConfig(agentPluginClassLoader);
        PluginConfig pluginConfig = getPluginConfig(agentPluginClassLoader, yamlAgentConfig);
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = getMethodPointcutConfig(agentPluginClassLoader, yamlAgentConfig);
        new AgentBuilder.Default()
                .type(new AgentJunction(methodPointcutConfigMap))
                .transform(new AgentTransform(methodPointcutConfigMap, agentPluginClassLoader))
                .with(new AgentListener())
                .installOn(inst);
        log.info("-------------------FlowSphereAgent启动成功-------------------");

    }

    private static PluginConfig getPluginConfig(AgentPluginClassLoader agentPluginClassLoader, YamlAgentConfig yamlAgentConfig) {
        return PluginConfigLoaderManager.getPluginConfigLoader(yamlAgentConfig.getPluginConfigDataSource().getType())
                .load(agentPluginClassLoader, yamlAgentConfig.getPluginConfigDataSource().getPros());
    }

    private static Map<String, Collection<YamlMethodPointcutConfig>> getMethodPointcutConfig(AgentPluginClassLoader agentPluginClassLoader,
                                                                                             YamlAgentConfig yamlAgentConfig) {
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = AgentBootstrap
                .loadPlugins(yamlAgentConfig.getPlugins(), agentPluginClassLoader);
        return methodPointcutConfigMap;
    }


}
