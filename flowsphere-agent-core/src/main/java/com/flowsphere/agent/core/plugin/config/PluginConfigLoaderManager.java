package com.flowsphere.agent.core.plugin.config;

import com.flowsphere.agent.core.plugin.config.local.LocalPluginConfigLoader;
import com.flowsphere.agent.core.plugin.config.nacos.NacosPluginConfigLoader;

import java.util.HashMap;
import java.util.Map;

public class PluginConfigLoaderManager {

    private static final Map<String, PluginConfigLoader> LOADER_MAP = new HashMap<>();

    static {
        LOADER_MAP.put(PluginConfigDataSourceTypeEnum.NACOS.getType(), new NacosPluginConfigLoader());
        LOADER_MAP.put(PluginConfigDataSourceTypeEnum.LOCAL.getType(), new LocalPluginConfigLoader());
    }


    public static PluginConfigLoader getPluginConfigLoader(String key) {
        return LOADER_MAP.get(key);
    }

}
