package com.flowsphere.agent.core.spi;

import com.flowsphere.agent.core.plugin.lifecycle.PluginLifecycleService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ServiceLoader;

public class AgentServiceLoader {

    public static Collection<PluginLifecycleService> load(Class<PluginLifecycleService> service) {
        Collection<PluginLifecycleService> result = new LinkedList<>();
        for (PluginLifecycleService each : ServiceLoader.load(service)) {
            result.add(each);
        }
        return result;
    }

}
