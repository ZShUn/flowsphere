package com.flowsphere.agent.core.config;

import com.flowsphere.agent.core.config.yaml.YamlPluginConfig;
import com.flowsphere.agent.core.yaml.YamlResolver;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PluginsConfigLoader {

    public static List<String> load(InputStream inputStream) {
        YamlPluginConfig yamlPluginConfig = YamlResolver.parsePluginConfig(inputStream);
        return Optional.ofNullable(yamlPluginConfig)
                .map(YamlPluginConfig::getPlugins)
                .orElse(new ArrayList<>());
    }

}
