package com.ancient.agent.core.classloader;

import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;

public class AgentClassLoaderContext {

    private static AgentPluginClassLoader DEFAULT_AGENT_PLUGIN_CLASS_LOADER;

    public AgentPluginClassLoader getPluginClassLoader() {
        return DEFAULT_AGENT_PLUGIN_CLASS_LOADER;
    }

    public static void initAgentClassLoaderContext(ClassLoader classLoader, List<JarFile> jarFileList) {
        if (Objects.isNull(DEFAULT_AGENT_PLUGIN_CLASS_LOADER)) {
            synchronized (AgentClassLoaderContext.class) {
                if (Objects.isNull(DEFAULT_AGENT_PLUGIN_CLASS_LOADER)) {
                    DEFAULT_AGENT_PLUGIN_CLASS_LOADER = new AgentPluginClassLoader(classLoader, jarFileList);
                }
            }
        }
    }

}
