package com.flowsphere.test.plugin.config;

import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.nacos.NacosPluginConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Properties;

public class NacosPluginConfigLoaderTest {

    @Test
    public void loadTest() {
        NacosPluginConfigLoader nacosPluginConfigLoader = new NacosPluginConfigLoader();
        Properties properties = new Properties();
        properties.setProperty("dataId", "default");
        properties.setProperty("groupId", "DEFAULT_GROUP");
        properties.setProperty("serverAddr", "127.0.0.1:8848");
        properties.setProperty("timeout", "3000");
        List<PluginConfig> pluginConfigList = nacosPluginConfigLoader.load(NacosPluginConfigLoader.class.getClassLoader(), properties);
        Assertions.assertTrue(!CollectionUtils.isEmpty(pluginConfigList));
        Assertions.assertTrue(pluginConfigList.get(0).getPluginName().equals("nacos"));
    }

}
