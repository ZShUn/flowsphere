package com.ancient.agent.core;

import com.ancient.agent.core.config.yaml.YamlMethodPointcutConfig;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AncientBootstrapTest {

    @Test
    public void loadPluginsTest() {
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = AncientBootstrap.loadPlugins();
        assertNotNull(methodPointcutConfigMap);
    }

}
