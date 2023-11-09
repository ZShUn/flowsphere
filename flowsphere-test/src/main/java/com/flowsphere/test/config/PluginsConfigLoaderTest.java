package com.flowsphere.test.config;

import com.flowsphere.agent.core.config.PluginsConfigLoader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PluginsConfigLoaderTest {

    @Test
    public void loadTest() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(PluginsConfigLoaderTest.class.getResource("/").getPath() + "agent.yaml"));
        List<String> plugins = PluginsConfigLoader.load(fileInputStream);
        assertNotNull(plugins);
        assertTrue(plugins.size() > 0);
    }

}
