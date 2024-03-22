package com.flowsphere.agent.core.plugin.config.datasource.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigLoader;
import com.flowsphere.common.util.JacksonUtils;
import lombok.SneakyThrows;

import java.util.*;

public class NacosPluginConfigLoader implements PluginConfigLoader {

    private static final String DEFAULT_TIMEOUT = "3000";

    private static final String DATA_ID = "dataId";

    private static final String GROUP_ID = "groupId";

    private static final String TIMEOUT = "timeout";

    @SneakyThrows
    @Override
    public PluginConfig load(ClassLoader classLoader, Properties properties) {
        String dataId = properties.getProperty(DATA_ID);
        String groupId = properties.getProperty(GROUP_ID);
        int timeout = Integer.parseInt(Optional.ofNullable(properties.getProperty(TIMEOUT))
                .orElse(DEFAULT_TIMEOUT));
        ConfigService configService = NacosFactory.createConfigService(properties);
        configService.addListener(dataId, groupId, new NacosConfigListener());
        String jsonStr = configService.getConfig(dataId, groupId, timeout);
        return JacksonUtils.toObj(jsonStr, PluginConfig.class);
    }


}
