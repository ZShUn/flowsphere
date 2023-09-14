package com.ancient.agent.core;

import com.ancient.agent.core.config.PluginsConfigLoader;
import com.ancient.agent.core.config.PointcutConfigLoader;
import com.ancient.agent.core.config.yaml.YamlClassPointcutConfig;
import com.ancient.agent.core.config.yaml.YamlMethodPointcutConfig;
import com.ancient.agent.core.utils.URLClassLoaderFactory;
import com.ancient.common.cache.JsonPath;
import com.ancient.common.cache.RuleCache;
import com.ancient.common.constant.CommonConstant;
import com.ancient.common.entity.RuleEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class AncientBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncientBootstrap.class);


    public static void loadRule() {
        try {
            File file = JsonPath.getPath();
            if (Objects.isNull(file)) {
                LOGGER.warn("rule.json file is null");
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            RuleEntity ruleEntity = objectMapper.readValue(file, RuleEntity.class);
            RuleCache.put(CommonConstant.RULE_KEY, ruleEntity);
        } catch (Exception e) {
            LOGGER.error("load rule config error", e);
        }
    }

    public static Map<String, Collection<YamlMethodPointcutConfig>> loadPlugins(List<URL> urlList) {
        //解析plugins配置
        URL[] pluginUrls = urlList.toArray(new URL[]{});
        ClassLoader classLoader = URLClassLoaderFactory.createClassLoader(pluginUrls, AncientBootstrap.class.getClassLoader());
        InputStream agentYamlInputStream = classLoader.getResourceAsStream(String.join(File.separator, "agent.yaml"));
        List<String> pluginNames = PluginsConfigLoader.load(agentYamlInputStream);
        Map<String, Collection<YamlMethodPointcutConfig>> methodPointcutConfigMap = new HashMap<>();
        for (String pluginName : pluginNames) {
            InputStream pluginClassLoader = classLoader.getResourceAsStream(String.join(File.separator, pluginName + "-agent.yaml"));
            List<YamlClassPointcutConfig> classPointcutConfigs = PointcutConfigLoader.load(pluginClassLoader);
            for (YamlClassPointcutConfig classPointcutConfig : classPointcutConfigs) {
                methodPointcutConfigMap.computeIfAbsent(classPointcutConfig.getClassName(), key -> classPointcutConfig.getMethodPointcutConfigs());
            }
        }
        return methodPointcutConfigMap;
    }


}
