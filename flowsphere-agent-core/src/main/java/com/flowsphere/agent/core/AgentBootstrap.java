package com.flowsphere.agent.core;

import com.flowsphere.agent.core.config.PointcutConfigLoader;
import com.flowsphere.agent.core.config.yaml.YamlClassPointcutConfig;
import com.flowsphere.agent.core.config.yaml.YamlMethodPointcutConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class AgentBootstrap {

    public static Map<String, Collection<YamlMethodPointcutConfig>> loadPlugins(Set<String> pluginNameSet, ClassLoader classLoader) {

        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = new HashMap<>();
        for (String pluginName : pluginNameSet) {
            List<YamlClassPointcutConfig> classPointcutConfigs = PointcutConfigLoader.load(pluginName, classLoader);
            for (YamlClassPointcutConfig classPointcutConfig : classPointcutConfigs) {
                methodPointcutConfigMap.computeIfAbsent(classPointcutConfig.getClassName(), key -> classPointcutConfig.getMethodPointcutConfigs());
            }
        }
        return methodPointcutConfigMap;
    }


}
