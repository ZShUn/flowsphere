package com.flowsphere.spring.cloud.service.provider.example.feign;

import com.flowsphere.common.tag.TagManager;
import com.flowsphere.spring.cloud.service.api.SpringCloudCApi;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SpringCloudProviderC1Feign implements SpringCloudCApi {

    @Autowired
    private DefaultMQProducer defaultMQProducer;


    @PostMapping("/service-c/helloWord")
    public String helloWord(@RequestBody String str) {
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
        return "[App=AncientSpringCloudProviderC1 Tag=" + TagManager.getTag() + "] param=" + str;
    }

}
