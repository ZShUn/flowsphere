package com.ancient.test;

import org.junit.jupiter.api.Test;

public class AgentProxyCore {

    public void instantHelloWord() {
        System.out.println("instantHelloWord");
    }

    @Test
    public void instantHelloWordTest() {
        AgentProxyCore myHelloWord = new AgentProxyCore();
        myHelloWord.instantHelloWord();
    }

}
