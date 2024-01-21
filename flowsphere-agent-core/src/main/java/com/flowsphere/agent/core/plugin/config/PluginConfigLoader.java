package com.flowsphere.agent.core.plugin.config;

import java.util.List;
import java.util.Properties;

public interface PluginConfigLoader {

    List<PluginConfig> load(ClassLoader classLoader, Properties properties);

}
