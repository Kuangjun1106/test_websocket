package com.kj.controller;

import com.kj.service.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @Autowired
    private WebSocket webSocket;

    @RequestMapping("/sendMessage")
    @ResponseBody
    public void sendMessage() {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new SendMessageThread());
            thread.start();
        }
    }

    /**
     * 随机发送信息
     */
    class SendMessageThread implements Runnable {
        @Override
        public void run() {
            String[] message = new String[]{"kj", "Sunflower", "firefly", "Toast"};
            webSocket.sendMessage(message[(int) (Math.random() * message.length)]);
        }

    }
}
