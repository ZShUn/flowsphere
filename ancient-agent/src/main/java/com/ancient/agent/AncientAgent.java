package com.ancient.agent;

import com.ancient.agent.core.AncientAgentJunction;
import com.ancient.agent.core.AncientAgentListener;
import com.ancient.agent.core.AncientAgentTransform;
import com.ancient.agent.core.AncientBootstrap;
import com.ancient.agent.core.config.PluginsConfigLoader;
import com.ancient.agent.core.config.PointcutConfigLoader;
import com.ancient.agent.core.config.yaml.YamlClassPointcutConfig;
import com.ancient.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.ancient.agent.core.utils.URLClassLoaderFactory;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AncientAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncientAgent.class);

    public static void premain(String agentArgs, Instrumentation inst) {
        LOGGER.info("-------------------AncientAgent开始启动-------------------");
        AncientBootstrap.loadRule();

        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap =  AncientBootstrap.loadPlugins();

        new AgentBuilder.Default()
                .type(new AncientAgentJunction(methodPointcutConfigMap))
                .transform(new AncientAgentTransform(methodPointcutConfigMap))
                .with(new AncientAgentListener())
                .installOn(inst);
        LOGGER.info("-------------------AncientAgent启动成功-------------------");

    }

}
