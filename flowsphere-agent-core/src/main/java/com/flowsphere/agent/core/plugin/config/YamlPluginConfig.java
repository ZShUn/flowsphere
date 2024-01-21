package com.flowsphere.agent.core.plugin.config;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class YamlPluginConfig {

    private List<PluginConfig> plugins = new LinkedList<>();

}
