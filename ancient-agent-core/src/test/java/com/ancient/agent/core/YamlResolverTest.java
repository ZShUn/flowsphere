package com.ancient.agent.core;

import com.ancient.agent.core.config.yaml.YamlPluginConfig;
import com.ancient.agent.core.yaml.YamlResolver;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class YamlResolverTest {

    @Test
    public void parseTest() throws FileNotFoundException {
        //需要读取具体的文件夹
        FileInputStream fileInputStream = new FileInputStream(new File(YamlResolverTest.class.getResource("/").getPath() + "agent.yaml"));
        YamlResolver customYamlParse = new YamlResolver();
        YamlPluginConfig yamlPluginConfig = customYamlParse.parsePluginConfig(fileInputStream);
        assertTrue(Objects.nonNull(yamlPluginConfig));
    }

}
