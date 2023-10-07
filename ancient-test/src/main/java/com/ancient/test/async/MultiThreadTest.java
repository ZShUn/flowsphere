package com.ancient.test.async;

import com.ancient.common.context.RuleContext;
import com.ancient.common.rule.entity.RuleEntity;
import com.ancient.common.rule.entity.VersionEntity;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MultiThreadTest {

    private static final String VERSION_STR = "1.0.2";

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    @Test
    public void runnableTest() {
        versionRuleReady();
        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                asyncVersionRuleValidate();
            }
        });
    }

    @Test
    public void nestedRunnableTest() {
        versionRuleReady();
        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                asyncVersionRuleValidate();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        asyncVersionRuleValidate();
                    }
                }).start();
            }
        });
    }

    @Test
    public void runnableMixCallableTest() throws ExecutionException, InterruptedException {
        versionRuleReady();
        Future future = EXECUTOR_SERVICE.submit(new Runnable() {
            @Override
            public void run() {
                asyncVersionRuleValidate();
                EXECUTOR_SERVICE.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        asyncVersionRuleValidate();
                        return null;
                    }
                });
            }
        });
        future.get();
    }


    @Test
    public void callableTest() {
        versionRuleReady();
        EXECUTOR_SERVICE.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                asyncVersionRuleValidate();
                return null;
            }
        });
    }


    @Test
    public void nestedCallableTest() throws ExecutionException, InterruptedException {
        versionRuleReady();
        Future future = EXECUTOR_SERVICE.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                asyncVersionRuleValidate();

                EXECUTOR_SERVICE.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        asyncVersionRuleValidate();
                        return null;
                    }
                });
                return null;
            }
        });
        future.get();
    }


    @Test
    public void callableMixRunnableTest() {
        versionRuleReady();
        EXECUTOR_SERVICE.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                asyncVersionRuleValidate();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        asyncVersionRuleValidate();
                    }
                }).start();

                return null;
            }
        });
    }


    private void versionRuleReady() {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setVersionEntity(new VersionEntity(VERSION_STR));
        RuleContext.set(ruleEntity);
    }


    private void asyncVersionRuleValidate() {
        RuleEntity result = RuleContext.get();
        assertNotNull(result);
        VersionEntity versionEntity = result.getVersionEntity();
        assertNotNull(versionEntity);
        assertEquals(versionEntity.getVersion(), VERSION_STR);
    }


}
