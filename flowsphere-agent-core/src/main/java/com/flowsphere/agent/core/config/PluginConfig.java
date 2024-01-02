package com.flowsphere.agent.core.config;

import lombok.Data;

import java.util.Properties;

@Data
public class PluginConfig {

    private String pluginName;

    private Properties props;

}
