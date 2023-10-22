package com.ancient.agent.core.context;


public class CustomContext {

    private String tag;

    private Thread originThread;

    public CustomContext(String tag) {
        this.tag = tag;
        this.originThread = Thread.currentThread();
    }

    public String getTag() {
        return tag;
    }

    public Thread getOriginThread() {
        return originThread;
    }
}
