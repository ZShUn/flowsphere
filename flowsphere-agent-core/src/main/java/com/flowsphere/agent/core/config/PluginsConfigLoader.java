package com.flowsphere.agent.core.config;

import com.flowsphere.agent.core.config.yaml.YamlPluginConfig;
import com.flowsphere.agent.core.utils.AgentPath;
import com.flowsphere.agent.core.yaml.YamlResolver;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PluginsConfigLoader {

    @SneakyThrows
    public static YamlPluginConfig load(ClassLoader classLoader) {
        return YamlResolver.parsePluginConfig(classLoader);
    }


}
