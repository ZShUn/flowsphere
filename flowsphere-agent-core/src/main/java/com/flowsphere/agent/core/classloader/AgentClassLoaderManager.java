package com.flowsphere.agent.core.classloader;

import com.flowsphere.agent.core.jar.PluginsJarLoader;
import com.flowsphere.agent.core.utils.URLUtils;

import java.net.URL;
import java.util.List;
import java.util.jar.JarFile;

public class AgentClassLoaderManager {

    public static AgentPluginClassLoader getAgentPluginClassLoader(ClassLoader classLoader) {
        List<URL> urlList = URLUtils.getPluginURL();
        List<JarFile> jarFileList = PluginsJarLoader.getJarFileList(urlList);
        return new AgentPluginClassLoader(classLoader, jarFileList);
    }

}
