package com.flowsphere.dubbo.service.c1.example.client;

import com.flowsphere.common.tag.TagManager;
import com.flowsphere.dubbo.api.DubboServiceCApi;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService(version = "1.0.0")
public class DubboServiceC1ApiImpl implements DubboServiceCApi {

    @Autowired
    private DefaultMQProducer defaultMQProducer;


    public String helloword(String str) {
        Message msg = null;
        try {
            msg = new Message("TopicTest",
                    "TagA",
                    "OrderID188",
                    str.getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.putUserProperty("user", "20");
            SendResult sendResult = defaultMQProducer.send(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "[FlowSphereDubboServiceC1ApiImpl Tag= " + TagManager.getTag() + "] :" + str;
    }

}
