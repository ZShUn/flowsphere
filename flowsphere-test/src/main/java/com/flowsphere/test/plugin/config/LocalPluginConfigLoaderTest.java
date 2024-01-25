package com.flowsphere.test.plugin.config;

import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.local.LocalPluginConfigLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class LocalPluginConfigLoaderTest {

    @Test
    public void loadTest() {
        LocalPluginConfigLoader localPluginConfigLoader = new LocalPluginConfigLoader();
        List<PluginConfig> pluginConfigList = localPluginConfigLoader.load(LocalPluginConfigLoader.class.getClassLoader(), null);
        Assertions.assertTrue(!CollectionUtils.isEmpty(pluginConfigList));
        Assertions.assertTrue(pluginConfigList.get(0).getPluginName().equals("nacos"));
    }

}
