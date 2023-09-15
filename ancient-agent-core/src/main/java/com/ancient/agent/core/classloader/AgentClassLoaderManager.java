package com.ancient.agent.core.classloader;

import java.util.List;
import java.util.jar.JarFile;

public class AgentClassLoaderManager {

    public static AgentPluginClassLoader getAgentPluginClassLoader(ClassLoader classLoader, List<JarFile> jarFileList) {
        return new AgentPluginClassLoader(classLoader, jarFileList);
    }

}
