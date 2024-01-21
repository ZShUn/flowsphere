package com.flowsphere.agent.core.plugin.config;

import lombok.Data;

import java.util.Properties;

@Data
public class PluginConfig {

    private String pluginName;

    private Properties props;

}
