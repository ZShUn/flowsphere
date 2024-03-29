package com.flowsphere.agent.plugin.rocketmq.consumer.sql;

import com.flowsphere.agent.core.context.CustomContextAccessor;
import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.interceptor.type.InstantMethodInterceptor;
import com.flowsphere.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.impl.consumer.PullResultExt;
import org.apache.rocketmq.common.message.MessageDecoder;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
public class ProcessPullResultInterceptor extends AbstractSqlInterceptor implements InstantMethodInterceptor {

    @Override
    protected void doBeforeMethod(CustomContextAccessor customContextAccessor, Object[] allArguments, Callable<?> callable, Method method, InstantMethodInterceptorResult instantMethodInterceptorResult) {
        PullResult pullResult = (PullResult) allArguments[1];
        PullResultExt pullResultExt = (PullResultExt) pullResult;
        if (PullStatus.FOUND == pullResult.getPullStatus()) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(pullResultExt.getMessageBinary());
            List<MessageExt> msgList = MessageDecoder.decodes(byteBuffer);
            Map<String, String> properties = msgList.get(0).getProperties();
            if (StringUtils.isNotBlank(properties.get(CommonConstant.TAG))) {
                if (log.isDebugEnabled()) {
                    log.debug("[FlowSphere] ProcessPullResultInterceptor rocketMQ processPullResult={}", properties.get(CommonConstant.TAG));
                }
                SubscriptionData subscriptionData = (SubscriptionData) allArguments[2];
                subscriptionData.setTagsSet(new HashSet<>());
            }
        }
    }


}
