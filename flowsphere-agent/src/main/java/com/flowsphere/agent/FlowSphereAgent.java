package com.flowsphere.agent;


import com.flowsphere.agent.core.AgentBootstrap;
import com.flowsphere.agent.core.AgentJunction;
import com.flowsphere.agent.core.AgentListener;
import com.flowsphere.agent.core.AgentTransform;
import com.flowsphere.agent.core.classloader.AgentClassLoaderManager;
import com.flowsphere.agent.core.classloader.AgentPluginClassLoader;
import com.flowsphere.agent.core.config.yaml.YamlAgentConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigLoader;
import com.flowsphere.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.flowsphere.agent.core.plugin.config.PluginsConfigLoaderManager;
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
        List<PluginConfig> pluginConfigList = PluginsConfigLoaderManager.getPluginConfigLoader(yamlAgentConfig.getPluginConfigDataSource().getType())
                .load(agentPluginClassLoader, yamlAgentConfig.getPluginConfigDataSource().getPros());
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = AgentBootstrap
                .loadPlugins(pluginConfigList.stream().map(PluginConfig::getPluginName)
                        .collect(Collectors.toSet()), agentPluginClassLoader);

        new AgentBuilder.Default()
                .type(new AgentJunction(methodPointcutConfigMap))
                .transform(new AgentTransform(methodPointcutConfigMap, agentPluginClassLoader, pluginConfigList))
                .with(new AgentListener())
                .installOn(inst);
        log.info("-------------------FlowSphereAgent启动成功-------------------");

    }

}
