package com.ancient.test.config;

import com.ancient.agent.core.config.PointcutConfigLoader;
import com.ancient.agent.core.config.yaml.YamlClassPointcutConfig;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PointcutConfigLoaderTest {

    @Test
    public void loadTest() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(PointcutConfigLoaderTest.class.getResource("/").getPath() + "example-agent.yaml"));
        List<YamlClassPointcutConfig> classPointcutConfigs = PointcutConfigLoader.load(fileInputStream);
        assertNotNull(classPointcutConfigs);
        assertTrue(classPointcutConfigs.size() > 0);
    }

}