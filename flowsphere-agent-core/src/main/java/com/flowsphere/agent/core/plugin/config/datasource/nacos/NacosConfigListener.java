package com.flowsphere.agent.core.plugin.config.datasource.nacos;

import com.alibaba.nacos.api.config.listener.Listener;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import com.flowsphere.common.concurrent.NamedThreadFactory;
import com.flowsphere.common.util.JacksonUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class NacosConfigListener implements Listener {

    private static final ExecutorService POOL = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1), new NamedThreadFactory("flowsphere-nacos-ds-update", true), new ThreadPoolExecutor.DiscardOldestPolicy());

    @Override
    public Executor getExecutor() {
        return POOL;
    }

    @Override
    public void receiveConfigInfo(String config) {
        PluginConfig pluginConfigList = JacksonUtils.toObj(config, PluginConfig.class);
        PluginConfigCache.put(pluginConfigList);
    }
}
