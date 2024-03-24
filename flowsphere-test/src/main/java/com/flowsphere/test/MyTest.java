package com.flowsphere.test;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.flowsphere.agent.core.plugin.config.PluginConfig;
import com.flowsphere.common.util.JacksonUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class MyTest {

    public static void main(String[] args) throws NacosException {
        ConfigService configService = NacosFactory.createConfigService("127.0.0.1:8848");
        String jsonStr = configService.getConfig("default", "DEFAULT_GROUP", 1000);
//        Map<String, Map<String, Object>> pluginMap = JacksonUtils.toObj(jsonStr, Map.class);
//        Map<String, Object> pluginConfigMap = pluginMap.get("nacos");
//        Properties properties = new Properties();
//        properties.putAll(pluginConfigMap);
//        Object value = pluginConfigMap.get("test");

        Properties properties = new Properties();
        properties.put("test","1");
        properties.put("test","2");
        System.out.println("3");

    }

    protected static Map<String, Object> asMap(Object object) {
        // YAML can have numbers as keys
        Map<String, Object> result = new LinkedHashMap();
        if (!(object instanceof Map)) {
            // A document can be a text literal
            result.put("document", object);
            return result;
        }

        Map<Object, Object> map = (Map<Object, Object>) object;
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = asMap(value);
            }
            if (key instanceof CharSequence) {
                result.put(key.toString(), value);
            } else {
                result.put("[" + key.toString() + "]", value);
            }
        }
        return result;
    }

}
