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
        //TODO 根据环境变量考虑初始化本地配置、APOLLO配置、NACOS配置
        return YamlResolver.parsePluginConfig(classLoader);
    }


}
