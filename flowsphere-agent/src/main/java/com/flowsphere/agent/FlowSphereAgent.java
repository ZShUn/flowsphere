package com.flowsphere.agent;


import com.flowsphere.agent.core.AgentBootstrap;
import com.flowsphere.agent.core.AgentJunction;
import com.flowsphere.agent.core.AgentListener;
import com.flowsphere.agent.core.AgentTransform;
import com.flowsphere.agent.core.classloader.AgentClassLoaderManager;
import com.flowsphere.agent.core.classloader.AgentPluginClassLoader;
import com.flowsphere.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.flowsphere.agent.core.jar.PluginsJarLoader;
import com.flowsphere.agent.core.utils.URLUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

@Slf4j
public class FlowSphereAgent {


    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("-------------------AncientAgent开始启动-------------------");
        List<URL> urlList = URLUtils.getPluginURL();
        List<JarFile> jarFileList = PluginsJarLoader.getJarFileList(urlList);
        AgentPluginClassLoader agentPluginClassLoader = AgentClassLoaderManager.getAgentPluginClassLoader(FlowSphereAgent.class.getClassLoader(),jarFileList);
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = AgentBootstrap.loadPlugins(urlList);
        new AgentBuilder.Default()
                .type(new AgentJunction(methodPointcutConfigMap))
                .transform(new AgentTransform(methodPointcutConfigMap, agentPluginClassLoader))
                .with(new AgentListener())
                .installOn(inst);
        log.info("-------------------AncientAgent启动成功-------------------");

    }

}
