package com.flowsphere.agent.plugin.rocketmq.config;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class RocketMQConfigManager {

    private static final Map<String, String> CONFIG = new ConcurrentHashMap<>();

    public static void init(Properties pros) {
        for (String key : pros.stringPropertyNames()) {
            CONFIG.put(key, pros.getProperty(key));
        }
    }

    public static String getConfig(String key) {
        return CONFIG.get(key);
    }

}
