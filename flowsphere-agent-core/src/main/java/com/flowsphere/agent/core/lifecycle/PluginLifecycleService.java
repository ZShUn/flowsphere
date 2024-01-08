package com.flowsphere.agent.core.lifecycle;

import com.flowsphere.agent.core.config.PluginConfig;

public interface PluginLifecycleService {

    void start(PluginConfig pluginConfig);

    default void close() {
    }


    String getType();

}
