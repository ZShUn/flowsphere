package com.flowsphere.agent.core.config.yaml;

import com.flowsphere.agent.core.config.PluginConfig;
import lombok.Data;

import java.util.List;

@Data
public class YamlPluginConfig {

    private List<PluginConfig> plugins;

}
