package com.example.demo.subscirbe;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiver1 {

    public void receiverMessage1(String message){
        System.out.println("lints >>> 通道:laowang1,消息:" + message);
    }
}
