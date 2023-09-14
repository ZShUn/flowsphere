package com.ancient.agent.core;

import com.ancient.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.ancient.agent.core.utils.URLUtils;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AncientBootstrapTest {

    @Test
    public void loadPluginsTest() {
        List<URL> urlList = URLUtils.getPluginURL();
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = AncientBootstrap.loadPlugins(urlList);
        assertNotNull(methodPointcutConfigMap);
    }

}
