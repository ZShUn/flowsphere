package com.ancient.agent.core.context;


public class CustomContext {

    private String ruleContextStr;

    private Thread originThread;

    public CustomContext(String ruleContextStr) {
        this.ruleContextStr = ruleContextStr;
        this.originThread = Thread.currentThread();
    }

    public String getRuleContextStr() {
        return ruleContextStr;
    }

    public Thread getOriginThread() {
        return originThread;
    }
}
