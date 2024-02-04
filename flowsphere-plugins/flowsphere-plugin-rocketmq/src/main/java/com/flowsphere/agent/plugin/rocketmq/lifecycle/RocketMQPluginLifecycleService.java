//package com.flowsphere.agent.plugin.rocketmq.lifecycle;
//
//import com.flowsphere.agent.core.plugin.config.PluginConfig;
//import com.flowsphere.agent.core.plugin.lifecycle.PluginLifecycleService;
//import com.flowsphere.agent.plugin.rocketmq.config.RocketMQConfigManager;
//import com.flowsphere.agent.plugin.rocketmq.constant.RocketMQConstant;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class RocketMQPluginLifecycleService implements PluginLifecycleService {
//
//    @Override
//    public void start(PluginConfig pluginConfig) {
//        if (!pluginConfig.getPluginName().equals(RocketMQConstant.ROCKETMQ)) {
//            return;
//        }
//        RocketMQConfigManager.init(pluginConfig.getProps());
//    }
//
//    @Override
//    public String getType() {
//        return RocketMQConstant.ROCKETMQ;
//    }
//
//
//}
