package com.flowsphere.agent.core.yaml;

import com.flowsphere.agent.core.config.yaml.YamlPluginConfig;
import com.flowsphere.agent.core.config.yaml.YamlPointcutConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class YamlResolver {

    public static YamlPointcutConfig parsePointcutConfig(InputStream inputStream) {
        return new Yaml(new YamlConstructor(YamlPointcutConfig.class)).loadAs(inputStream, YamlPointcutConfig.class);
    }

    public static YamlPluginConfig parsePluginConfig(InputStream inputStream) {
        return new Yaml(new YamlConstructor(YamlPluginConfig.class)).loadAs(inputStream, YamlPluginConfig.class);
    }

}
