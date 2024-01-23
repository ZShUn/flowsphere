package com.flowsphere.agent.core.plugin.config;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class PluginConfigManager {

    private static volatile List<PluginConfig> CONFIG_STORE = new CopyOnWriteArrayList<>();

    public static void addAll(List<PluginConfig> propertiesMap) {
        CONFIG_STORE.addAll(propertiesMap);
    }

    public static void clear() {
        CONFIG_STORE.clear();
    }

    public static void refresh(List<PluginConfig> propertiesMap) {
        clear();
        addAll(propertiesMap);
    }

    public static void updateValue(String pluginName, String key, Object value) {
        for (PluginConfig pluginConfig : CONFIG_STORE) {
            if (pluginConfig.getPluginName().equals(pluginName)) {
                Properties properties = pluginConfig.getProps();
                properties.put(key, value);
            }
        }
    }


    public static Object getConfig(String pluginName, String key) {
        for (PluginConfig pluginConfig : CONFIG_STORE) {
            if (pluginConfig.getPluginName().equals(pluginName)) {
                Properties properties = pluginConfig.getProps();
                return properties.get(key);
            }
        }
        return null;
    }

}