package com.ancient.agent;

import com.ancient.agent.core.AncientAgentJunction;
import com.ancient.agent.core.AncientAgentListener;
import com.ancient.agent.core.AncientAgentTransform;
import com.ancient.agent.core.AncientBootstrap;
import com.ancient.agent.core.classloader.AgentClassLoaderManager;
import com.ancient.agent.core.classloader.AgentPluginClassLoader;
import com.ancient.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.ancient.agent.core.jar.PluginsJarLoader;
import com.ancient.agent.core.utils.URLUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

@Slf4j
public class AncientAgent {


    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("-------------------AncientAgent开始启动-------------------");
        List<URL> urlList = URLUtils.getPluginURL();
        List<JarFile> jarFileList = PluginsJarLoader.getJarFileList(urlList);
        AgentPluginClassLoader agentPluginClassLoader = AgentClassLoaderManager.getAgentPluginClassLoader(AncientAgent.class.getClassLoader(),jarFileList);
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = AncientBootstrap.loadPlugins(urlList);
        new AgentBuilder.Default()
                .type(new AncientAgentJunction(methodPointcutConfigMap))
                .transform(new AncientAgentTransform(methodPointcutConfigMap, agentPluginClassLoader))
                .with(new AncientAgentListener())
                .installOn(inst);
        log.info("-------------------AncientAgent启动成功-------------------");

    }

}
