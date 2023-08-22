package com.ancient.agent.core;

import com.ancient.agent.core.config.entity.YamlPluginConfig;
import com.ancient.agent.core.config.entity.YamlPointcutConfig;
import com.ancient.agent.core.yaml.YamlResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomYamlParseTest {

    public static void main(String[] args) throws FileNotFoundException {
//        FileInputStream fileInputStream = new FileInputStream(new File(CustomYamlParseTest.class.getResource("/").getPath() + "Agent.yaml"));
        FileInputStream fileInputStream = new FileInputStream(new File(CustomYamlParseTest.class.getResource("/").getPath()).getParentFile());
        YamlResolver customYamlParse = new YamlResolver();
        YamlPluginConfig yamlPluginConfig = customYamlParse.parsePluginConfig(fileInputStream);
    }


}
