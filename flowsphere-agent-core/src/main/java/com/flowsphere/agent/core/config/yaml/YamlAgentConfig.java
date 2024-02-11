package com.flowsphere.agent.core.config.yaml;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Data
public class YamlAgentConfig {

    private PluginConfigDataSource pluginConfigDataSource;

    private List<String> plugins = new ArrayList<>();

    @Data
    public static class PluginConfigDataSource {

        private String type;

        private Properties pros;

    }

}
