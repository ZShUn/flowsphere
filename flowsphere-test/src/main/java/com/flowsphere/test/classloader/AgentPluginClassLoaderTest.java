package com.flowsphere.test.classloader;

import com.flowsphere.agent.core.classloader.AgentPluginClassLoader;
import com.flowsphere.agent.core.jar.PluginsJarLoader;
import com.flowsphere.agent.core.utils.URLUtils;
import com.flowsphere.test.interceptor.CustomInterceptor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.jar.JarFile;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AgentPluginClassLoaderTest {


    @Test
    public void loadTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<JarFile> jarFileList = PluginsJarLoader.getJarFileList(URLUtils.getPluginURL());
        AgentPluginClassLoader classLoader = new AgentPluginClassLoader(AgentPluginClassLoaderTest.class.getClassLoader(), jarFileList);

        CustomInterceptor methodInterceptor = (CustomInterceptor) Class.forName("com.flowsphere.test.interceptor.CustomInterceptor", true, classLoader).newInstance();
        assertNotNull(methodInterceptor);
    }


}