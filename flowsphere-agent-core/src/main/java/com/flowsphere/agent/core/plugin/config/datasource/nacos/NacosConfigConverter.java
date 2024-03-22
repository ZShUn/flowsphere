package com.flowsphere.agent.core.plugin.config.datasource.nacos;

import com.flowsphere.agent.core.plugin.config.PluginConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class NacosConfigConverter {

    public static List<PluginConfig> convert(Map<String, Map<String, Object>> pluginMap) {
        List<PluginConfig> result = new ArrayList<>();
        for (String key : pluginMap.keySet()) {
            PluginConfig pluginConfig = new PluginConfig();
            pluginConfig.setPluginName(key);

            Properties properties = new Properties();
            properties.putAll(pluginMap.get(key));
            pluginConfig.setProps(properties);

            result.add(pluginConfig);
        }
        return result;
    }

}
