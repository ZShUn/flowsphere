package com.flowsphere.agent.core.config.yaml;

import lombok.Data;

import java.util.Properties;

@Data
public class YamlAgentConfig {

    private PluginConfigDataSource pluginConfigDataSource;


    @Data
    public static class PluginConfigDataSource {

        private String type;

        private Properties pros;

    }

}
