package com.ancient.test.classloader;

import com.ancient.agent.core.classloader.AgentPluginClassLoader;
import com.ancient.agent.core.jar.PluginsJarLoader;
import com.ancient.agent.core.utils.URLUtils;
import com.ancient.test.interceptor.CustomInterceptor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.jar.JarFile;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AgentPluginClassLoaderTest {


    @Test
    public void loadTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<JarFile> jarFileList = PluginsJarLoader.getJarFileList(URLUtils.getPluginURL());
        AgentPluginClassLoader classLoader = new AgentPluginClassLoader(AgentPluginClassLoaderTest.class.getClassLoader(), jarFileList);

        CustomInterceptor methodInterceptor = (CustomInterceptor) Class.forName("com.ancient.test.interceptor.CustomInterceptor", true, classLoader).newInstance();
        assertNotNull(methodInterceptor);
    }


}
