package com.flowsphere.test.plugin.elasticjob;

import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.plugin.config.PluginConfigCache;
import com.flowsphere.agent.plugin.elastic.job.ElasticJobInstantMethodInterceptor;
import com.flowsphere.agent.plugin.elastic.job.constant.ElasticJobConstant;
import com.flowsphere.common.util.NetUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.concurrent.Callable;

public class ElasticJobInstantMethodInterceptorTest {


    @Test
    public void beforeInstantMethodInterceptorResultIsFalseTest() {
        ElasticJobInstantMethodInterceptor interceptor = new ElasticJobInstantMethodInterceptor();
        try (MockedStatic<PluginConfigCache> pluginConfigManagerMockedStatic = Mockito.mockStatic(PluginConfigCache.class);
             MockedStatic<NetUtils> netUtilsMockedStatic = Mockito.mockStatic(NetUtils.class)) {
            netUtilsMockedStatic.when(() -> NetUtils.getIpAddress()).thenReturn("127.0.0.1");
            pluginConfigManagerMockedStatic.when(() -> PluginConfigCache.get().getElasticJobConfig().isGrayEnabled()).thenReturn(true);
            pluginConfigManagerMockedStatic.when(() -> PluginConfigCache.get().getElasticJobConfig().getIp()).thenReturn("127.0.0.1");
            InstantMethodInterceptorResult instantMethodInterceptorResult = new InstantMethodInterceptorResult();
            interceptor.beforeMethod(null, new Object[]{}, new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return null;
                }
            }, null, instantMethodInterceptorResult);
            Assertions.assertTrue(!instantMethodInterceptorResult.isContinue());
        } catch (Exception e) {
            throw e;
        }
    }

    @Test
    public void beforeInstantMethodInterceptorResultIsTrueTest() {
        ElasticJobInstantMethodInterceptor interceptor = new ElasticJobInstantMethodInterceptor();
        try (MockedStatic<PluginConfigCache> pluginConfigManagerMockedStatic = Mockito.mockStatic(PluginConfigCache.class);
             MockedStatic<NetUtils> netUtilsMockedStatic = Mockito.mockStatic(NetUtils.class)) {
            netUtilsMockedStatic.when(() -> NetUtils.getIpAddress()).thenReturn("127.0.0.1");
            pluginConfigManagerMockedStatic.when(() -> PluginConfigCache.get().getElasticJobConfig().isGrayEnabled()).thenReturn(true);
            pluginConfigManagerMockedStatic.when(() -> PluginConfigCache.get().getElasticJobConfig().getIp()).thenReturn("192.168.0.1");
            InstantMethodInterceptorResult instantMethodInterceptorResult = new InstantMethodInterceptorResult();
            interceptor.beforeMethod(null, new Object[]{}, new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return null;
                }
            }, null, instantMethodInterceptorResult);
            Assertions.assertTrue(instantMethodInterceptorResult.isContinue());
        } catch (Exception e) {
            throw e;
        }
    }

}
