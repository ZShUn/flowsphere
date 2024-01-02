package com.flowsphere.test.config;

import com.flowsphere.agent.core.config.PluginsConfigLoader;
import com.flowsphere.agent.core.config.yaml.YamlPluginConfig;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PluginsConfigLoaderTest {

    @Test
    public void loadTest() throws FileNotFoundException {
        YamlPluginConfig yamlPluginConfig = PluginsConfigLoader.load(PluginsConfigLoaderTest.class.getClassLoader());
        assertNotNull(yamlPluginConfig.getPlugins());
        assertTrue(yamlPluginConfig.getPlugins().size() > 0);
    }

}