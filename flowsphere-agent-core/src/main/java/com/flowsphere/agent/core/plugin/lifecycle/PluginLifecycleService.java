package com.flowsphere.agent.core.plugin.lifecycle;

import com.flowsphere.agent.core.plugin.config.PluginConfig;

public interface PluginLifecycleService {

    void start(PluginConfig pluginConfig);

    default void close() {
    }


    String getType();

}
