package com.flowsphere.agent;


import com.flowsphere.agent.core.AgentBootstrap;
import com.flowsphere.agent.core.AgentJunction;
import com.flowsphere.agent.core.AgentListener;
import com.flowsphere.agent.core.AgentTransform;
import com.flowsphere.agent.core.classloader.AgentClassLoaderManager;
import com.flowsphere.agent.core.classloader.AgentPluginClassLoader;
import com.flowsphere.agent.core.config.PluginConfig;
import com.flowsphere.agent.core.config.PluginsConfigLoader;
import com.flowsphere.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.flowsphere.agent.core.config.yaml.YamlPluginConfig;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class FlowSphereAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("-------------------FlowSphereAgent开始启动-------------------");
        AgentPluginClassLoader agentPluginClassLoader = AgentClassLoaderManager.getAgentPluginClassLoader(FlowSphereAgent.class.getClassLoader());
        YamlPluginConfig yamlPluginConfig = PluginsConfigLoader.load(agentPluginClassLoader);
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = AgentBootstrap
                .loadPlugins(yamlPluginConfig.getPlugins().stream().map(PluginConfig::getPluginName)
                        .collect(Collectors.toSet()), agentPluginClassLoader);

        new AgentBuilder.Default()
                .type(new AgentJunction(methodPointcutConfigMap))
                .transform(new AgentTransform(methodPointcutConfigMap, agentPluginClassLoader, yamlPluginConfig))
                .with(new AgentListener())
                .installOn(inst);
        log.info("-------------------FlowSphereAgent启动成功-------------------");

    }

}
