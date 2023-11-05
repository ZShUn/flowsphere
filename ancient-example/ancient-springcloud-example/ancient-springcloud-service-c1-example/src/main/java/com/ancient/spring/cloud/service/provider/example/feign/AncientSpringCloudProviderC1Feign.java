package com.ancient.spring.cloud.service.provider.example.feign;

import com.ancient.common.rule.TagManager;
import com.ancient.spring.cloud.service.api.AncientSpringCloudCApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class AncientSpringCloudProviderC1Feign implements AncientSpringCloudCApi {

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
            log.info("发送消息时 tag={}", TagManager.getTag());
            SendResult sendResult = defaultMQProducer.send(msg);
            log.info("发送结果 sendResult={}", sendResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "[App=AncientSpringCloudProviderC1 Tag=" + TagManager.getTag() + "] param=" + str;
    }

}
