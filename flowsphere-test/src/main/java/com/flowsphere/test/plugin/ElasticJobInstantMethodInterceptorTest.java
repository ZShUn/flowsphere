package com.flowsphere.test.plugin;

import com.flowsphere.agent.core.interceptor.template.InstantMethodInterceptorResult;
import com.flowsphere.agent.core.plugin.config.PluginConfigManager;
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
        try (MockedStatic<PluginConfigManager> pluginConfigManagerMockedStatic = Mockito.mockStatic(PluginConfigManager.class);
             MockedStatic<NetUtils> netUtilsMockedStatic = Mockito.mockStatic(NetUtils.class)) {
            netUtilsMockedStatic.when(() -> NetUtils.getIpAddress()).thenReturn("127.0.0.1");
            pluginConfigManagerMockedStatic.when(() -> PluginConfigManager.getConfig(ElasticJobConstant.ELASTIC_JOB, ElasticJobConstant.ELASTIC_JOB_GRAY_ENABLED)).thenReturn(true);
            pluginConfigManagerMockedStatic.when(() -> PluginConfigManager.getConfig(ElasticJobConstant.ELASTIC_JOB, ElasticJobConstant.ELASTIC_JOB_EXECUTE_IP)).thenReturn("127.0.0.1");
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
        try (MockedStatic<PluginConfigManager> pluginConfigManagerMockedStatic = Mockito.mockStatic(PluginConfigManager.class);
             MockedStatic<NetUtils> netUtilsMockedStatic = Mockito.mockStatic(NetUtils.class)) {
            netUtilsMockedStatic.when(() -> NetUtils.getIpAddress()).thenReturn("127.0.0.1");
            pluginConfigManagerMockedStatic.when(() -> PluginConfigManager.getConfig(ElasticJobConstant.ELASTIC_JOB, ElasticJobConstant.ELASTIC_JOB_GRAY_ENABLED)).thenReturn(true);
            pluginConfigManagerMockedStatic.when(() -> PluginConfigManager.getConfig(ElasticJobConstant.ELASTIC_JOB, ElasticJobConstant.ELASTIC_JOB_EXECUTE_IP)).thenReturn("192.168.0.1");
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
