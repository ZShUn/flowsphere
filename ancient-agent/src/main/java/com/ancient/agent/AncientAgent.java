package com.ancient.agent;

import com.ancient.agent.core.AncientAgentListener;
import com.ancient.agent.core.AncientAgentTransform;
import com.ancient.agent.core.AncientBootstrap;
import com.ancient.agent.core.AncientAgentJunction;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class AncientAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(AncientAgent.class);

    public static void premain(String agentArgs, Instrumentation inst) {
        LOGGER.info("-------------------AncientAgent开始启动-------------------");
        AncientBootstrap.load();
        new AgentBuilder.Default()
                .type(new AncientAgentJunction())
                .transform(new AncientAgentTransform())
                .with(new AncientAgentListener())
                .installOn(inst);
        LOGGER.info("-------------------AncientAgent启动成功-------------------");

    }

}
