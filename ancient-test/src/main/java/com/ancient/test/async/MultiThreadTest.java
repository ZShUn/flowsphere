package com.ancient.test.async;

import com.ancient.common.context.RuleContext;
import com.ancient.common.entity.RuleEntity;
import com.ancient.common.entity.VersionEntity;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MultiThreadTest {

    private static final String VERSION_STR = "1.0.2";

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    @Test
    public void runnableTest() {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setVersionEntity(new VersionEntity(VERSION_STR));
        RuleContext.set(ruleEntity);

        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                RuleEntity result = RuleContext.get();
                assertNotNull(result);
                VersionEntity versionEntity = result.getVersionEntity();
                assertNotNull(versionEntity);
                assertEquals(versionEntity.getVersion(), VERSION_STR);
            }
        });
    }


    @Test
    public void callableTest() {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setVersionEntity(new VersionEntity(VERSION_STR));
        RuleContext.set(ruleEntity);

        EXECUTOR_SERVICE.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                RuleEntity result = RuleContext.get();
                assertNotNull(result);
                VersionEntity versionEntity = result.getVersionEntity();
                assertNotNull(versionEntity);
                assertEquals(versionEntity.getVersion(), VERSION_STR);
                return null;
            }
        });
    }


}
