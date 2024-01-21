package com.flowsphere.agent.core.plugin.lifecycle;

import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.spi.AgentServiceLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class PluginLifecycleServiceManager {

    private static final AtomicBoolean STARED_FLAG = new AtomicBoolean(false);


    public static void init(List<PluginConfig> plugins) {
        if (STARED_FLAG.compareAndSet(false, true)) {
            start(plugins);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> close(plugins)));
        }
    }

    private static void start(List<PluginConfig> plugins) {
        for (PluginConfig pluginConfig : plugins) {
            AgentServiceLoader.load(PluginLifecycleService.class)
                    .stream()
                    .filter(pluginLifecycleService -> pluginLifecycleService.getType().equals(pluginConfig.getPluginName()))
                    .findFirst()
                    .ifPresent(plugin -> plugin.start(pluginConfig));
        }
    }


    private static void close(List<PluginConfig> plugins) {
        for (PluginConfig pluginConfig : plugins) {
            AgentServiceLoader.load(PluginLifecycleService.class)
                    .stream()
                    .filter(pluginLifecycleService -> pluginLifecycleService.getType().equals(pluginConfig.getPluginName()))
                    .findFirst()
                    .ifPresent(plugin -> plugin.close());
        }
    }


}
