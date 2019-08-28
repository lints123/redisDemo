package com.example.demo.subscirbe;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    public void receiverMessage(String message){
        System.out.println("lints >>> 通道:laowang,消息:" + message);
    }
    public void receiverMessage1(String message){
        System.out.println("lints >>> 通道:laowang1,消息:" + message);
    }
}
