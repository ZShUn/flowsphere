package com.flowsphere.agent.core.plugin.config;

import com.flowsphere.agent.core.plugin.config.support.ElasticJobConfig;
import com.flowsphere.agent.core.plugin.config.support.RocketMQConfig;
import com.flowsphere.agent.core.plugin.config.support.SpringCloudGatewayConfig;
import lombok.Data;

import java.util.Properties;

@Data
public class PluginConfig {

    private String pluginName;

    private Properties props;

    private RocketMQConfig rocketMQConfig;

    private ElasticJobConfig elasticJobConfig;

    private SpringCloudGatewayConfig springCloudGatewayConfig;

}
