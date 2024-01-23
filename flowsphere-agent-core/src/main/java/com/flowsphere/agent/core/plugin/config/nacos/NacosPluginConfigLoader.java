package com.flowsphere.agent.core.plugin.config.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigLoader;
import com.flowsphere.common.util.JacksonUtils;
import lombok.SneakyThrows;

import java.util.*;

public class NacosPluginConfigLoader implements PluginConfigLoader {

    private static final int DEFAULT_TIMEOUT = 3000;

    private static final String DATA_ID = "dataId";

    private static final String GROUP_ID = "groupId";

    @SneakyThrows
    @Override
    public List<PluginConfig> load(ClassLoader classLoader, Properties properties) {
        String dataId = properties.getProperty(DATA_ID);
        String groupId = properties.getProperty(GROUP_ID);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String jsonStr = configService.getConfig(dataId, groupId, DEFAULT_TIMEOUT);
        Map<String, Map<String, Object>> pluginMap = JacksonUtils.toObj(jsonStr, Map.class);
        return NacosConfigConverter.convert(pluginMap);
    }




}
