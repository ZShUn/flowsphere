package com.flowsphere.test;

import com.flowsphere.agent.core.AgentBootstrap;
import com.flowsphere.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.flowsphere.agent.core.utils.URLUtils;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BootstrapTest {

    @Test
    public void loadPluginsTest() {
        List<URL> urlList = URLUtils.getPluginURL();
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = AgentBootstrap.loadPlugins(urlList);
        assertNotNull(methodPointcutConfigMap);
    }

}
