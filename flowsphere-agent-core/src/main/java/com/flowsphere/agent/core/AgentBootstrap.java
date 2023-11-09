package com.flowsphere.agent.core;

import com.flowsphere.agent.core.config.PluginsConfigLoader;
import com.flowsphere.agent.core.config.PointcutConfigLoader;
import com.flowsphere.agent.core.config.yaml.YamlClassPointcutConfig;
import com.flowsphere.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.flowsphere.agent.core.utils.URLClassLoaderManager;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AgentBootstrap {

    public static Map<String, Collection<YamlMethodPointcutConfig>> loadPlugins(List<URL> urlList) {
        //解析plugins配置
        URL[] pluginUrls = urlList.toArray(new URL[]{});
        ClassLoader classLoader = URLClassLoaderManager.createClassLoader(pluginUrls, AgentBootstrap.class.getClassLoader());
        InputStream agentYamlInputStream = classLoader.getResourceAsStream(String.join(File.separator, "agent.yaml"));
        List<String> pluginNames = PluginsConfigLoader.load(agentYamlInputStream);
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = new HashMap<>();
        for (String pluginName : pluginNames) {
            InputStream pluginClassLoader = classLoader.getResourceAsStream(String.join(File.separator, pluginName + "-agent.yaml"));
            List<YamlClassPointcutConfig> classPointcutConfigs = PointcutConfigLoader.load(pluginClassLoader);
            for (YamlClassPointcutConfig classPointcutConfig : classPointcutConfigs) {
                methodPointcutConfigMap.computeIfAbsent(classPointcutConfig.getClassName(), key -> classPointcutConfig.getMethodPointcutConfigs());
            }
        }
        return methodPointcutConfigMap;
    }


}
