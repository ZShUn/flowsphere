package com.flowsphere.agent.core.yaml;

import com.flowsphere.agent.core.config.yaml.YamlAgentConfig;
import com.flowsphere.agent.core.plugin.config.YamlPluginConfig;
import com.flowsphere.agent.core.config.yaml.YamlPointcutConfig;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;

public class YamlResolver {

    @SneakyThrows
    public static YamlPointcutConfig parsePointcutConfig(String pluginName, ClassLoader classLoader) {
        try (InputStream agentYamlInputStream = classLoader.getResourceAsStream(String.join(File.separator, pluginName + "-agent.yaml"))) {
            return new Yaml(new YamlConstructor(YamlPointcutConfig.class)).loadAs(agentYamlInputStream, YamlPointcutConfig.class);
        }
    }

    @SneakyThrows
    public static YamlPluginConfig parsePluginConfig(ClassLoader classLoader) {
        try (InputStream agentYamlInputStream = classLoader
                .getResourceAsStream(String.join(File.separator, "plugin-config.yaml"))) {
            return new Yaml(new YamlConstructor(YamlPluginConfig.class)).loadAs(agentYamlInputStream, YamlPluginConfig.class);
        }
    }

    @SneakyThrows
    public static YamlAgentConfig parseAgentConfig(ClassLoader classLoader) {
        try (InputStream agentYamlInputStream = classLoader
                .getResourceAsStream(String.join(File.separator, "agent.yaml"))) {
            return new Yaml(new YamlConstructor(YamlAgentConfig.class)).loadAs(agentYamlInputStream, YamlAgentConfig.class);
        }
    }

}
