package com.flowsphere.agent.core.config;

import com.flowsphere.agent.core.config.yaml.YamlClassPointcutConfig;
import com.flowsphere.agent.core.config.yaml.YamlPointcutConfig;
import com.flowsphere.agent.core.yaml.YamlResolver;

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
