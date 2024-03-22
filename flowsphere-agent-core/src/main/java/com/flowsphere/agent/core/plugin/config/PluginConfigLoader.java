package com.flowsphere.agent.core.plugin.config;

import java.util.Properties;

public interface PluginConfigLoader {

    PluginConfig load(ClassLoader classLoader, Properties properties);

}
