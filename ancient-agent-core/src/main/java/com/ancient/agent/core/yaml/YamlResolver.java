package com.ancient.agent.core.yaml;

import com.ancient.agent.core.config.entity.YamlPluginConfig;
import com.ancient.agent.core.config.entity.YamlPointcutConfig;
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
