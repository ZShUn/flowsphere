package com.flowsphere.agent.core.plugin.config.datasource.local;

import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigLoader;
import com.flowsphere.agent.core.plugin.config.YamlPluginConfig;
import com.flowsphere.agent.core.yaml.YamlResolver;

import java.util.List;
import java.util.Properties;

public class LocalPluginConfigLoader implements PluginConfigLoader {

    @Override
    public PluginConfig load(ClassLoader classLoader, Properties properties) {
        YamlPluginConfig yamlPluginConfig = YamlResolver.parsePluginConfig(classLoader);
        return yamlPluginConfig.getPluginConfig();
    }

}
