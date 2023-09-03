package com.ancient.agent.core.config;

import com.ancient.agent.core.config.yaml.YamlClassPointcutConfig;
import com.ancient.agent.core.config.yaml.YamlPointcutConfig;
import com.ancient.agent.core.yaml.YamlResolver;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PointcutConfigLoader {

    public static List<YamlClassPointcutConfig> load(InputStream inputStream) {
        return Optional.ofNullable(YamlResolver.parsePointcutConfig(inputStream))
                .map(YamlPointcutConfig::getPointcutConfigs)
                .orElse(new ArrayList<>());
    }

}
