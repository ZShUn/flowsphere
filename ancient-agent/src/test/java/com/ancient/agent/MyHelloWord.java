package com.ancient.agent;

import org.junit.jupiter.api.Test;

public class MyHelloWord {

    public void instantHelloWord() {
        System.out.println("instantHelloWord");
    }

    @Test
    public void instantHelloWordTest() {
        MyHelloWord myHelloWord = new MyHelloWord();
        myHelloWord.instantHelloWord();
    }

}
